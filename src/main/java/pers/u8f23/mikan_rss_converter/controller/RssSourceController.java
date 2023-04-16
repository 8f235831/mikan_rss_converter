package pers.u8f23.mikan_rss_converter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.u8f23.mikan_rss_converter.service.RssSourceService;

@Controller
@RequestMapping ("rss/source")
public class RssSourceController
{
	private final RssSourceService rssSourceService;

	public RssSourceController(
		@Autowired RssSourceService rssSourceService
	)
	{
		this.rssSourceService = rssSourceService;
	}

	@ResponseBody
	@RequestMapping (path = "list", method = RequestMethod.GET, produces =
		"application/xml;charset=utf-8")
	public String rssSource()
	{
		return rssSourceService.getFollowRss();
	}
}
