package com.sap.cache.bo;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sap.cache.utils.CacheUtils;

@Component
public class CacheBOImpl implements CacheBO {
	@Value(value = "${periodInSec}")
	private String periodInSec;

	private final Map<String, SoftReference<Object>> cache = new ConcurrentHashMap<>();

	public CacheBOImpl() {

	}

	@Override
	public Map<String, Object> getAllCache() {
		Map<String, Object> map = new ConcurrentHashMap<>();
		for (Map.Entry<String, SoftReference<Object>> entry : cache.entrySet())
			map.put(entry.getKey(), entry.getValue().get());
		return map;
	}

	@Override
	public Optional<SoftReference<Object>> getCache(String key) {
		return Optional.ofNullable(cache.get(key));
	}

	@Override
	public void add(String key, String value) {

		if (key == null)
			return;
		if (value == null)
			cache.remove(key);

		else {

			long expTime = (System.currentTimeMillis() / 1000) + Long.parseLong(periodInSec);
			SoftReference<Object> ref = new SoftReference<Object>(value);
			cache.put(key, ref);
			CacheUtils.putInQueue(key, ref, expTime);

		}

	}

	@Override
	public void removeCache(String key) {
		cache.remove(key);
	}

	public Map<String, SoftReference<Object>> getCache() {
		return cache;
	}

}
