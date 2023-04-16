package pers.u8f23.mikan_rss_converter.service;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pers.u8f23.mikan_rss_converter.config.Cons;
import pers.u8f23.mikan_rss_converter.config.ResponseCode;
import pers.u8f23.mikan_rss_converter.entity.BaseResponse;
import pers.u8f23.mikan_rss_converter.entity.RssCache;
import pers.u8f23.mikan_rss_converter.entity.RssDocument;
import pers.u8f23.mikan_rss_converter.entity.RssFollow;
import pers.u8f23.mikan_rss_converter.entity.mapper.RssFollowMapper;
import pers.u8f23.mikan_rss_converter.util.RequestUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 8f23
 * @create 2023/4/16-20:06
 */
@Service
@Scope ("singleton")
@Slf4j
public class RssSourceService
{
	private final RssFollowMapper rssFollowMapper;
	private final AtomicBoolean taskRunning;
	private final Object addTasksLock;
	private final Map<Long, RssCache> rssCacheMap;

	public RssSourceService(
		@Autowired RssFollowMapper rssFollowMapper
	)
	{
		this.rssFollowMapper = rssFollowMapper;
		this.taskRunning = new AtomicBoolean(false);
		this.addTasksLock = new Object();
		this.rssCacheMap = Collections.synchronizedMap(new TreeMap<>());
	}

	@Scheduled (fixedDelay = Cons.CONTENT_POLLING_PERIOD)
	public void polling()
	{
		if (taskRunning.get())
		{
			return;
		}
		synchronized (this.addTasksLock)
		{
			if (taskRunning.get())
			{
				return;
			}
			taskRunning.set(true);
			log.debug("RssSourceService polling() started.");
			int baseTime =
				(int) ((System.currentTimeMillis() -
				        Cons.CONTENT_OUT_OF_DATE_PERIOD) / 1000L);
			List<RssFollow> follows = rssFollowMapper.getFollowList();
			follows.stream()
				.filter(rssFollow -> (
					rssFollow != null
					&& rssFollow.getEnabled() == 1
					&& rssFollow.getLastUpdateSucceedTime() < baseTime
				))
				.forEach(rssFollow -> {
					RssDocument document;
					try
					{
						String xml = RequestUtils.getFromUrl(
							rssFollow.getRssSite(),
							Cons.URL_CONNECT_MAX_RETRY_TIMES
						);
						document = new RssDocument(xml);
					}
					catch (Exception e)
					{
						log.info("Failed to update from {} .",
							rssFollow.getRssSite());
						log.trace("Cause by ", e);
						rssFollowMapper.notifyUpdate(rssFollow.getId(), false);
						taskRunning.set(false);
						return;
					}
					log.info("Succeed to update from {} .",
						rssFollow.getRssSite());
					rssFollowMapper.notifyUpdate(rssFollow.getId(), true);
					rssCacheMap.put(rssFollow.getId(), new RssCache(
						rssFollow.getId(),
						document
					));
				});
			log.debug("RssSourceService polling() finished.");
			taskRunning.set(false);
		}
	}

	public BaseResponse<String> getRawDocumentById(long id)
	{
		RssCache cache = rssCacheMap.get(id);
		if (cache == null)
		{
			return ResponseCode.DEFAULT_FAILURE.nullResponse("当前无缓存！");
		}
		return ResponseCode.DEFAULT_SUCCESS
			.bodyResponse(cache.getDocument().toXml());
	}

	public String getFollowRss()
	{
		List<RssFollow> follows = rssFollowMapper.getFollowList();
		LinkedList<RssDocument> documentsInCache = new LinkedList<>();
		long currTime = System.currentTimeMillis();
		AtomicInteger outOfDateCounter = new AtomicInteger();
		AtomicInteger cacheMissCounter = new AtomicInteger();
		follows.stream()
			.filter(follow -> follow.getEnabled() == 1)
			.forEach((follow) -> {
				RssCache cache = rssCacheMap.get(follow.getId());
				if (cache == null)
				{
					cacheMissCounter.getAndIncrement();
					return;
				}
				documentsInCache.add(cache.getDocument()
					.filter(follow.getRegexFilter()));
				if ((currTime - cache.getUpdateTimestamp()) >
				    Cons.CONTENT_OUT_OF_DATE_PERIOD)
				{
					outOfDateCounter.getAndIncrement();
				}
			});
		RssDocument document = new RssDocument(
			"Mikan Project RSS 缓存",
			"",
			"Mikan Project RSS 缓存 \n" +
			"当前加载失败[" + cacheMissCounter + "]个，" +
			"加载成功[" + (documentsInCache.size()) + "]个，" +
			"其中[" + outOfDateCounter + "]个已过期。",
			documentsInCache
		);
		log.debug("{}", JSON.toJSONString(document));
		return document.toXml();
	}
}
