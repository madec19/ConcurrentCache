package com.sap.cache.controller;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.cache.bo.CacheBO;
import com.sap.cache.constants.CacheConstant;
import com.sap.cache.dto.ResponseDTO;

/**
 * @author VALAR-MORGULIS
 *
 */
@Controller
public class CacheController {

	@Autowired
	private CacheBO cacheBO;

	/**
	 * method to get all cache data
	 *
	 */

	@RequestMapping(value = "/getallcache", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResponseDTO> getAllCache() {
		ResponseDTO response = new ResponseDTO();
		Map<String, Object> data = cacheBO.getAllCache();
		response.setStatus(CacheConstant.SUCCESS);

		if (!data.isEmpty()) {
			response.setData(data);
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} else {
			response.setData(CacheConstant.NO_DATA_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}

	}

	/**
	 * method to get cache based on key
	 * @param key
	 */
	@RequestMapping(value = "/getcache/{key}", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getCache(@PathVariable("key") String key) {

		ResponseDTO response = new ResponseDTO();
		response.setStatus(CacheConstant.SUCCESS);
		Optional<SoftReference<Object>> op = cacheBO.getCache(key);
		if (op.isPresent()) {
			response.setData(op.get().get());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} else {
			response.setData(CacheConstant.KEY_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

	}

	/**
	 * method to remove key from cache
	 *
	 */
	@RequestMapping(value = "/removecache/{key}", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeCache(@PathVariable("key") String key) {
		cacheBO.removeCache(key);
	}

	/**
	 * method to insert data into cache
	 * @param key
	 * @param value
	 */

	@RequestMapping(value = "/createcache/{key}/{value}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> add(@PathVariable("key") String key, @PathVariable("value") String value) {
		cacheBO.add(key, value);
		return ResponseEntity.created(null).build();
	}

}
