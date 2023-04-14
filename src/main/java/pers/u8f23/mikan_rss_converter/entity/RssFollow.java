package pers.u8f23.mikan_rss_converter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * RSS关注列表。
 *
 * @author 8f23
 * @create 2023/4/14-17:56
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RssFollow
{
	private long id;
	private String rssSite;
	private String regexFilter;
	private String comment;
	private byte enabled;
	private int followAddedTime;
	private int followModifiedTime;
	private int lastUpdateSucceedTime;
	private int updateContinueFailCounter;
}
