package pers.u8f23.mikan_rss_converter.entity;

import lombok.Getter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.util.LinkedList;
import java.util.List;

@Getter
public class RssDocument {
    private final String title;
    private final String link;
    private final String description;
    private final List<RssItem> items;

    public RssDocument(String xmlString) throws DocumentException {
        Document document = DocumentHelper.parseText(xmlString);
        title = document.selectSingleNode("/rss/channel/title").getText();
        link = document.selectSingleNode("/rss/channel/link").getText();
        description = document.selectSingleNode("/rss/channel/description").getText();
        List<Node> itemNodes = document.selectNodes("/rss/channel/item");
        items = new LinkedList<>();
        for (Node itemNode : itemNodes) {
            items.add(new RssItem(itemNode));
        }
    }
}
