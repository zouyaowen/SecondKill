package org.zyw.secondkill.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.zyw.secondkill.service.CacheService;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class CacheServiceImpl implements CacheService {

	private static Cache<String, Object> commonCache = null;

	static {
		commonCache = CacheBuilder.newBuilder()
				// 设置初始容量
				.initialCapacity(10)
				// 设置最大存储100
				.maximumSize(100)
				// 设置写多少秒后过期
				.expireAfterWrite(60, TimeUnit.SECONDS).build();
	}

	@Override
	public void setCommonCache(String key, Object object) {
		commonCache.put(key, object);
	}

	@Override
	public Object getCommonCache(String key) {
		return commonCache.getIfPresent(key);
	}

}
