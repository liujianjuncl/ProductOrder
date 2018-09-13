package com.nii.desktop.util.conf;

import java.util.HashMap;
import java.util.Map;

public class LocalCache {

    // 缓存Map
    private static Map<String, CacheContent> map = new HashMap<>();
    private static LocalCache localCache = new LocalCache();

    private LocalCache() {
    }

    public static String getLocalCache(String key) {
        CacheContent cc = map.get(key);

        if (null == cc) {
            return null;
        }

        long currentTime = System.currentTimeMillis();

        if (cc.getCacheMillis() > 0 && currentTime - cc.getCreateTime() > cc.getCacheMillis()) {
            // 超过缓存过期时间,返回null
            map.remove(key);
            return null;
        } else {
            return cc.getElement();
        }
    }

    public void setLocalCache(String key, int cacheMillis, String value) {
        long currentTime = System.currentTimeMillis();
        CacheContent cc = new CacheContent(cacheMillis, value, currentTime);
        map.put(key, cc);
    }

    public static LocalCache getInStance() {
        return localCache;
    }

    class CacheContent {
        // 缓存生效时间
        private int cacheMillis;
        // 缓存对象
        private String element;
        // 缓存创建时间
        private long createTime;

        public CacheContent(int cacheMillis, String element, long createTime) {
            super();
            this.cacheMillis = cacheMillis;
            this.element = element;
            this.createTime = createTime;
        }

        public int getCacheMillis() {
            return cacheMillis;
        }

        public void setCacheMillis(int cacheMillis) {
            this.cacheMillis = cacheMillis;
        }

        public String getElement() {
            return element;
        }

        public void setElement(String element) {
            this.element = element;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
