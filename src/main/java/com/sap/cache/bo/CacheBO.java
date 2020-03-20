package com.sap.cache.bo;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Optional;

public interface CacheBO {

	Map<String,Object> getAllCache();
	Optional<SoftReference<Object>> getCache(String key);
	void add(String key, String value);
	void removeCache(String key);
	public Map<String, SoftReference<Object>> getCache();
}
