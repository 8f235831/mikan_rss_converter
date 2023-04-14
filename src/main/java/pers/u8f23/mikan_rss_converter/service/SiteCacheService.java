package pers.u8f23.mikan_rss_converter.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.u8f23.mikan_rss_converter.config.Cons;

import java.util.Map;
import java.util.TreeMap;

@Service
public class SiteCacheService {
    private static final Map<String, CacheItem> MAP = new TreeMap<>();
    private static final Object LOCK = new Object();

    private final MainService mainService;

    public SiteCacheService(@Autowired MainService mainService){
        this.mainService = mainService;
    }

    public String getWithPut(String path) throws Exception {
        CacheItem result;
        String content;
        synchronized (LOCK) {
            result = MAP.get(path);
            if (result == null) {
                content = mainService.getFromUrl(path);
                MAP.put(path, new CacheItem(content, System.currentTimeMillis()));
            } else if (System.currentTimeMillis() - result.updateTime < Cons.SITE_CACHE_OUT_OF_DATE_INTERVAL) {
                content = result.content;
            } else {
                content = mainService.getFromUrl(path);
                result.updateTime = System.currentTimeMillis();
            }
        }
        return content;
    }

    @AllArgsConstructor
    private static class CacheItem {
        private String content;
        private long updateTime;
    }
}
