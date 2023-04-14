package pers.u8f23.mikan_rss_converter;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.u8f23.mikan_rss_converter.entity.RssDocument;
import pers.u8f23.mikan_rss_converter.service.MainService;

import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest
@Slf4j
class ApplicationTests {
    private static final String EXAMPLE_RSS_FILE_PATH = "src/test/resources/example_rss.xml";

    @Autowired
    MainService mainService;

    @Test
    void testXmlParser() throws Exception {
        byte[] fileBytes;
        try (InputStream inputStream = new FileInputStream(EXAMPLE_RSS_FILE_PATH)) {
            fileBytes = inputStream.readAllBytes();
        }
        String xmlContent = new String(fileBytes);
        RssDocument document = new RssDocument(xmlContent);
        String documentJson = JSON.toJSONString(document);
        log.info("document: {}", documentJson);
    }

    @Test
    void testXmlParserOnline() throws Exception {
        String s = mainService.getFromUrl("https://mikanani.me/RSS/Bangumi?bangumiId=3028&subgroupid=382");
        RssDocument document = new RssDocument(s);
        String documentJson = JSON.toJSONString(document);
        log.info("document: {}", documentJson);
    }

}
