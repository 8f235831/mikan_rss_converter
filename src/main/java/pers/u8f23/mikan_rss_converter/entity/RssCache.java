package pers.u8f23.mikan_rss_converter.entity;

import lombok.Getter;

/**
 * @author 8f23
 * @create 2023/4/16-20:37
 */
@Getter
public class RssCache
{
	private final long id;
	private final RssDocument document;
	private final long updateTimestamp;

	public RssCache(long id, RssDocument document)
	{
		this.id = id;
		this.document = document;
		this.updateTimestamp = System.currentTimeMillis();
	}
}
