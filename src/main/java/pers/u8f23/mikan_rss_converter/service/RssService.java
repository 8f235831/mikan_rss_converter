package pers.u8f23.mikan_rss_converter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pers.u8f23.mikan_rss_converter.config.ResponseCode;
import pers.u8f23.mikan_rss_converter.entity.BaseResponse;
import pers.u8f23.mikan_rss_converter.entity.RssFollow;
import pers.u8f23.mikan_rss_converter.entity.mapper.RssFollowMapper;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 8f23
 * @create 2023/4/14-19:19
 */
@Slf4j
@Service
public class RssService
{
	private static final Pattern SITE_ILLEGAL_PATTERN =
		Pattern.compile("[%_\\[\\]]");

	private final RssFollowMapper rssFollowMapper;

	public RssService(@Autowired RssFollowMapper rssFollowMapper)
	{
		this.rssFollowMapper = rssFollowMapper;
	}

	public BaseResponse<List<RssFollow>> getFollowList(
		long fromId,
		int pageSize,
		String sitePrefix,
		String siteMatcher)
	{
		if (fromId < 0)
		{
			return ResponseCode.ILLEGAL_REQUEST_PARAM_FAILURE
				.nullResponse("fromId < 0");
		}
		if (pageSize <= 0)
		{
			return ResponseCode.ILLEGAL_REQUEST_PARAM_FAILURE
				.nullResponse("pageSize <= 0");
		}
		if (sitePrefix != null)
		{
			if (sitePrefix.isEmpty())
			{
				sitePrefix = null;

			}
			else if (SITE_ILLEGAL_PATTERN.matcher(sitePrefix).find())
			{
				return ResponseCode.ILLEGAL_REQUEST_PARAM_FAILURE
					.nullResponse("Illegal param [sitePrefix]");
			}
		}
		if (siteMatcher != null)
		{
			if (siteMatcher.isEmpty())
			{
				siteMatcher = null;
			}
			else if (SITE_ILLEGAL_PATTERN.matcher(siteMatcher).find())
			{
				return ResponseCode.ILLEGAL_REQUEST_PARAM_FAILURE
					.nullResponse("Illegal param [siteMatcher]");
			}
		}
		log.debug("RssFollowMapper.getFollowList({}, {}, {}, {})",
			fromId, pageSize, sitePrefix, siteMatcher);
		List<RssFollow> result = rssFollowMapper
			.getFollowList(fromId, pageSize, sitePrefix, siteMatcher);
		return ResponseCode.DEFAULT_SUCCESS.bodyResponse(result);
	}

	public BaseResponse<RssFollow> getFollowById(long id)
	{
		if (id < 0)
		{
			return ResponseCode.ILLEGAL_REQUEST_PARAM_FAILURE
				.nullResponse("id < 0");
		}
		log.debug("RssFollowMapper.getFollowById({})", id);
		List<RssFollow> result = rssFollowMapper.getFollowById(id);
		if (result == null || result.isEmpty())
		{
			return ResponseCode.NO_SUCH_ELEMENT_FAILURE
				.nullResponse();
		}
		if (result.size() > 1)
		{
			return ResponseCode.DATA_ACCESS_FAILURE
				.nullResponse();
		}
		return ResponseCode.DEFAULT_SUCCESS.bodyResponse(result.get(0));
	}

	public BaseResponse<Object> addFollow(String rssSite, String regexFilter,
		String comment)
	{
		if (rssSite == null || rssSite.isBlank())
		{
			return ResponseCode.ILLEGAL_REQUEST_PARAM_FAILURE
				.nullResponse("Illegal param [rssSite]");
		}
		if (regexFilter == null || regexFilter.isBlank())
		{
			regexFilter = "";
		}
		if (comment == null || comment.isBlank())
		{
			comment = "";
		}
		log.debug("RssFollowMapper.addFollow({}, {}, {})",
			rssSite, regexFilter, comment);
		rssFollowMapper.addFollow(rssSite, regexFilter, comment);
		return ResponseCode.DEFAULT_SUCCESS.nullResponse();
	}

	public BaseResponse<Object> deleteFollowById(long id)
	{
		if (id < 0)
		{
			return ResponseCode.ILLEGAL_REQUEST_PARAM_FAILURE
				.nullResponse("id < 0");
		}
		rssFollowMapper.deleteFollowById(id);
		return ResponseCode.DEFAULT_SUCCESS.nullResponse();
	}
}
