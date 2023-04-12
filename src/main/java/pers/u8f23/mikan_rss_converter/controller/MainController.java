package pers.u8f23.mikan_rss_converter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.u8f23.mikan_rss_converter.service.MainService;

@RestController
public class MainController {
    private final MainService mainService = new MainService();

    @RequestMapping(path = "convert", method = RequestMethod.GET, produces = "application/xml;charset=utf-8")
    public String convert(@RequestParam(value = "srcUrl") String srcUrl) throws Exception {
        return mainService.getFromUrl(srcUrl);
    }
}
