package pers.u8f23.mikan_rss_converter.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Node;
import org.dom4j.XPath;

import java.util.Map;

@Getter
@Slf4j
public class RssItem
{
	private final String guid;
	private final String link;
	private final String title;
	private final String description;
	private final String pubDate;
	private final String torrentUrl;
	private final long length;

	public RssItem(Node itemNode)
	{
		guid = itemNode.selectSingleNode("guid").getText();
		link = itemNode.selectSingleNode("link").getText();
		title = itemNode.selectSingleNode("title").getText();
		description = itemNode.selectSingleNode("description").getText();
		XPath pubDatePath = itemNode.createXPath("//ns:pubDate");
		pubDatePath.setNamespaceURIs(Map.of("ns", "https://mikanani.me/0.1/"));
		pubDate = pubDatePath.selectSingleNode(itemNode).getText();
		torrentUrl = itemNode.selectSingleNode("enclosure").valueOf("@name");
		length = Long.parseLong(itemNode.selectSingleNode("enclosure").valueOf("@length"));
	}
}
