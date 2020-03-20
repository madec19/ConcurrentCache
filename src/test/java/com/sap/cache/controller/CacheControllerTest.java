package com.sap.cache.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sap.cache.bo.CacheBO;
import com.sap.cache.evictionstrategy.SizeBasedEvictionStrategy;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CacheController.class)
public class CacheControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CacheBO cacheBO;

	@MockBean
	private SizeBasedEvictionStrategy sizeBasedEvictionStrategy;

	@Value(value = "${maxCap}")
	private String maxCapacity;

	@Test
	public void add() throws Exception {
		Mockito.doNothing().when(cacheBO).add("ma", "manoj");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createcache/ma/manoj");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

}
