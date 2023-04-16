package pers.u8f23.mikan_rss_converter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.u8f23.mikan_rss_converter.entity.RssDocument;
import pers.u8f23.mikan_rss_converter.service.MainService;

@Controller
public class MainController
{
	@RequestMapping (path = "", method = RequestMethod.GET)
	public String index()
	{
		return "index.html";
	}
}
