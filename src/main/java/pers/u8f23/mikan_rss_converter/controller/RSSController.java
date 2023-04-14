package pers.u8f23.mikan_rss_converter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.u8f23.mikan_rss_converter.entity.BaseResponse;
import pers.u8f23.mikan_rss_converter.entity.RssFollow;
import pers.u8f23.mikan_rss_converter.service.RssService;

import java.util.List;

@RestController
@RequestMapping ("rss")
public class RSSController
{
	private final RssService rssService;

	public RSSController(
		@Autowired RssService rssService
	)
	{
		this.rssService = rssService;
	}

	@ResponseBody
	@RequestMapping (
		path = "followList",
		method = RequestMethod.GET
	)
	public BaseResponse<List<RssFollow>> getFollowList(
		@RequestParam (value = "fromId")
			long fromId,
		@RequestParam (value = "pageSize")
			int pageSize,
		@RequestParam (value = "sitePrefix", required = false,
		               defaultValue = "")
			String sitePrefix,
		@RequestParam (value = "siteMatcher", required = false,
		               defaultValue = "")
			String siteMatcher)
	{
		return rssService.getFollowList(fromId, pageSize, sitePrefix,
			siteMatcher);
	}

	@ResponseBody
	@RequestMapping (
		path = "getFollow",
		method = RequestMethod.GET
	)
	public BaseResponse<RssFollow> getFollowById(
		@RequestParam ("id") long id)
	{
		return rssService.getFollowById(id);
	}

	@ResponseBody
	@RequestMapping (
		path = "deleteFollow",
		method = RequestMethod.GET
	)
	public BaseResponse<Object> deleteFollowById(
		@RequestParam ("id") long id
	)
	{
		return rssService.deleteFollowById(id);
	}

	@ResponseBody
	@RequestMapping (
		path = "addFollow",
		method = RequestMethod.GET
	)
	public BaseResponse<Object> addFollow(
		@RequestParam ("rssSite") String rssSite,
		@RequestParam ("regexFilter") String regexFilter,
		@RequestParam ("comment") String comment
	)
	{
		return rssService.addFollow(rssSite, regexFilter, comment);
	}
}
