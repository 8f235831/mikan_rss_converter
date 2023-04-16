package pers.u8f23.mikan_rss_converter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.u8f23.mikan_rss_converter.entity.BaseResponse;
import pers.u8f23.mikan_rss_converter.entity.RssFollow;
import pers.u8f23.mikan_rss_converter.service.RssFollowService;

import java.util.List;

@RestController
@RequestMapping ("rss/follow")
public class RssFollowController
{
	private final RssFollowService rssService;

	public RssFollowController(
		@Autowired RssFollowService rssService
	)
	{
		this.rssService = rssService;
	}

	@ResponseBody
	@RequestMapping (
		path = "list",
		method = RequestMethod.GET
	)
	public BaseResponse<List<RssFollow>> getFollowList()
	{
		return rssService.getFollowList();
	}

	@ResponseBody
	@RequestMapping (
		path = "get",
		method = RequestMethod.GET
	)
	public BaseResponse<RssFollow> getFollowById(
		@RequestParam ("id") long id)
	{
		return rssService.getFollowById(id);
	}

	@ResponseBody
	@RequestMapping (
		path = "delete",
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
		path = "add",
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

	@ResponseBody
	@RequestMapping (
		path = "modify",
		method = RequestMethod.GET
	)
	public BaseResponse<Object> modifyFollowById(
		@RequestParam ("id") long id,
		@RequestParam (value = "rssSite", required = false) String rssSite,
		@RequestParam (value = "regexFilter", required = false)
			String regexFilter,
		@RequestParam (value = "comment", required = false) String comment,
		@RequestParam (value = "enabled", required = false) Integer enabled
	)
	{
		return rssService.modifyFollowById(id, rssSite, regexFilter,
			comment, enabled);
	}
}
