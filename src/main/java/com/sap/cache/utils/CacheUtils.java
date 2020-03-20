package com.sap.cache.utils;

import java.lang.ref.SoftReference;
import java.util.concurrent.DelayQueue;

import com.sap.cache.dto.DelayedCacheObject;

public class CacheUtils {
	private final static DelayQueue<DelayedCacheObject> cleaningUpQueue = new DelayQueue<>();

	public static void putInQueue(String key, SoftReference<Object> ref, long expTime) {
		cleaningUpQueue.put(new DelayedCacheObject(key, ref, expTime));

	}

	public static DelayQueue<DelayedCacheObject> getCleaningupqueue() {
		return cleaningUpQueue;
	}

}
