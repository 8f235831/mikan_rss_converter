package pers.u8f23.mikan_rss_converter.entity;

import lombok.Getter;
import org.dom4j.*;

import java.util.LinkedList;
import java.util.List;

@Getter
public class RssDocument
{
	private final String title;
	private final String link;
	private final String description;
	private final List<RssItem> items;

	public RssDocument(String xmlString) throws DocumentException
	{
		Document document = DocumentHelper.parseText(xmlString);
		title = document.selectSingleNode("/rss/channel/title").getText();
		link = document.selectSingleNode("/rss/channel/link").getText();
		description = document.selectSingleNode("/rss/channel/description").getText();
		List<Node> itemNodes = document.selectNodes("/rss/channel/item");
		items = new LinkedList<>();
		for (Node itemNode : itemNodes)
		{
			items.add(new RssItem(itemNode));
		}
	}

	public String toXml()
	{
		Element root = DocumentHelper.createElement("rss");
		root.addAttribute("version", "2.0");
		Element channel = DocumentHelper.createElement("channel");
		root.add(channel);
		Element globalTitle = DocumentHelper.createElement("title");
		globalTitle.setText(this.title);
		channel.add(globalTitle);
		Element globalLink = DocumentHelper.createElement("link");
		globalLink.setText(this.link);
		channel.add(globalLink);
		Element globalDescription = DocumentHelper.createElement("description");
		globalDescription.setText(this.description);
		channel.add(globalDescription);
		// add items.
		for (RssItem item : this.items)
		{
			Element itemNode = DocumentHelper.createElement("item");
			channel.add(itemNode);
			// guid
			Element guid = DocumentHelper.createElement("guid");
			itemNode.add(guid);
			guid.addAttribute("isPermaLink", "false");
			guid.setText(item.getGuid());
			// link
			Element link = DocumentHelper.createElement("link");
			itemNode.add(link);
			guid.setText(item.getLink());
			// title
			Element title = DocumentHelper.createElement("title");
			itemNode.add(title);
			guid.setText(item.getTitle());
			// description
			Element description = DocumentHelper.createElement("description");
			itemNode.add(description);
			guid.setText(item.getDescription());
			// enclosure
			Element enclosure = DocumentHelper.createElement("enclosure");
			itemNode.add(enclosure);
			enclosure.addAttribute("type", "application/x-bittorrent");
			enclosure.addAttribute("length", Long.toString(item.getLength()));
			enclosure.addAttribute("url", item.getTorrentUrl());
		}

		Document document = DocumentHelper.createDocument(root);
		return document.asXML();
	}
}
