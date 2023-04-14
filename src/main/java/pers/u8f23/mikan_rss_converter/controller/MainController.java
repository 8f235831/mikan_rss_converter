package pers.u8f23.mikan_rss_converter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.u8f23.mikan_rss_converter.entity.RssDocument;
import pers.u8f23.mikan_rss_converter.service.MainService;
import pers.u8f23.mikan_rss_converter.service.SiteCacheService;

@Controller
public class MainController {
    private final MainService mainService = new MainService();
    private final SiteCacheService siteCacheService = new SiteCacheService();


    @RequestMapping(path = "", method = RequestMethod.GET)
    private String index() {
        return "index.html";
    }

    @ResponseBody
    @RequestMapping(path = "convert", method = RequestMethod.GET, produces = "application/xml;charset=utf-8")
    public String convert(@RequestParam(value = "srcUrl") String srcUrl) throws Exception {
        String src = mainService.getFromUrl(srcUrl);
        RssDocument document = new RssDocument(src);
        return document.toXml();
    }
/*
    @ResponseBody
    @RequestMapping(path = "")
    public String getData(){
        return ""
    }
    */

}
