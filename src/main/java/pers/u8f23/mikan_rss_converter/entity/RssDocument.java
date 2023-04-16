package pers.u8f23.mikan_rss_converter.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.*;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@Slf4j
public class RssDocument
{
	private final String title;
	private final String link;
	private final String description;
	private final LinkedList<RssItem> items;

	private RssDocument(String title, String link, String description,
		LinkedList<RssItem> items)
	{
		this.title = title;
		this.link = link;
		this.description = description;
		this.items = items;
	}

	public RssDocument(String xmlString) throws DocumentException
	{
		Document document = DocumentHelper.parseText(xmlString);
		title = document.selectSingleNode("/rss/channel/title").getText();
		link = document.selectSingleNode("/rss/channel/link").getText();
		description =
			document.selectSingleNode("/rss/channel/description").getText();
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
		Element globalDescription = DocumentHelper.createElement(
			"description");
		globalDescription.setText(this.description);
		channel.add(globalDescription);
		// add items.
		for (RssItem item : this.items)
		{
			Element itemNode = DocumentHelper.createElement("item");
			channel.add(itemNode);
			// guid
			Element guid = DocumentHelper.createElement("guid");
			guid.addAttribute("isPermaLink", "false");
			guid.setText(item.getGuid());
			itemNode.add(guid);
			// link
			Element link = DocumentHelper.createElement("link");
			link.setText(item.getLink());
			itemNode.add(link);
			// title
			Element title = DocumentHelper.createElement("title");
			title.setText(item.getTitle());
			itemNode.add(title);
			// description
			Element description = DocumentHelper.createElement("description");
			description.setText(item.getDescription());
			itemNode.add(description);
			// enclosure
			Element enclosure = DocumentHelper.createElement("enclosure");
			enclosure.addAttribute("type", "application/x-bittorrent");
			enclosure.addAttribute("length", Long.toString(item.getLength()));
			enclosure.addAttribute("url", item.getTorrentUrl());
			itemNode.add(enclosure);
		}

		Document document = DocumentHelper.createDocument(root);
		return document.asXML();
	}

	public RssDocument filter(String regex)
	{

		if (regex == null || regex.isEmpty())
		{
			return this;
		}
		Pattern pattern;
		try
		{
			pattern = Pattern.compile(regex);
		}
		catch (Exception e)
		{
			return this;
		}
		LinkedList<RssItem> result = new LinkedList<>();
		this.items.stream()
			.filter((item -> pattern.matcher(item.getTitle()).find()))
			.forEach(result::add);
		return new RssDocument(
			this.title,
			this.link,
			this.description,
			result
		);
	}

	public RssDocument(String title, String link,
		String description, List<RssDocument> subDocuments)
	{
		this.title = title;
		this.link = link;
		this.description = description;
		this.items = new LinkedList<>();
		for (RssDocument subDocument : subDocuments)
		{
			this.items.addAll(subDocument.items);
		}
	}
}
