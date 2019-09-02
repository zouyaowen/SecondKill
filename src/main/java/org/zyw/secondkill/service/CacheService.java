package org.zyw.secondkill.service;

/**
 * 封装本地缓存服务类
 * 
 * @author zyw
 *
 */
public interface CacheService {
	/**
	 * 存储数据
	 * 
	 * @param key
	 * @param object
	 */
	void setCommonCache(String key, Object object);

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	Object getCommonCache(String key);
}
