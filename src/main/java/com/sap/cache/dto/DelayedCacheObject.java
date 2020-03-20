package com.sap.cache.dto;

import java.lang.ref.SoftReference;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedCacheObject implements Delayed {

	private final String key;

	private final SoftReference<Object> ref;
	private final long expTime;

	public DelayedCacheObject(String key, SoftReference<Object> ref, long expTime) {
		this.key = key;
		this.ref = ref;
		this.expTime = expTime;
	}

	@Override
	public int compareTo(Delayed obj) {
		return Long.compare(expTime, ((DelayedCacheObject) obj).expTime);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(expTime - System.currentTimeMillis() / 1000, TimeUnit.SECONDS);
	}

	public String getKey() {
		return key;
	}

	public SoftReference<Object> getRef() {
		return ref;
	}

	public long getExpTime() {
		return expTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DelayedCacheObject other = (DelayedCacheObject) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
	

}
