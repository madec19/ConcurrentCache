package com.sap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sap.cache.constants.CacheConstant;
import com.sap.cache.evictionstrategy.SizeBasedEvictionStrategy;
import com.sap.cache.evictionstrategy.TimeBasedEvictionStrategy;


@Component
public class CacheConfig {

	@Autowired
	private SizeBasedEvictionStrategy sizeBasedEvictionStrategy;
	@Autowired
	private TimeBasedEvictionStrategy timeBasedEvictionStrategy;

	@Value(value = "${evictionType}")
	private String evictionType;

	@PostConstruct
	public void init() {

		Thread cleanerThread = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				if (evictionType.equals(CacheConstant.EVICTION_TYPE_TIME)) {
					timeBasedEvictionStrategy.evict();

				} else if (evictionType.equals(CacheConstant.EVICTION_TYPE_SIZE)) {
					sizeBasedEvictionStrategy.evict();

				}
			}
		});
		cleanerThread.setDaemon(true);
		cleanerThread.start();
	}

}
