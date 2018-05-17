package com.myretail.productdetail.controller;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.myretail.productdetail.model.Price;
import com.myretail.productdetail.model.Product;
import com.myretail.productdetail.service.ProductInfoService;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ProductController.class)
@WebMvcTest(value=ProductController.class, secure = false)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
    @MockBean
    private  ProductInfoService productInfoService;
	
    private int MOVIE_ID=13860428;

	Product prodDetail=null;
	Price prodPrice= new Price();
	@Before
	public void setup() {
		prodPrice.setId(MOVIE_ID);
		prodPrice.setCurrencyCode("USD");
		prodPrice.setPrice(new BigDecimal(100));
		prodDetail= new Product(MOVIE_ID,"The Big Lebowski (Blu-ray)",prodPrice);
	}
	
	
	@Test
	public void getProductDetailsTest() throws Exception {
		Mockito.when(productInfoService.getProductInfo(MOVIE_ID)).thenReturn(prodDetail);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/product/"+MOVIE_ID).accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"price\":{\"price\":100,\"currencyCode\":\"USD\"}}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test(expected = MethodArgumentTypeMismatchException.class)
	public void getProductDetailsInvalidRequestTest() throws Exception,MethodArgumentTypeMismatchException {
		String productId="ABC123";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/product/"+productId).accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());
			throw 	result.getResolvedException();
	}
	
	@Test
	public void updateProductDetailsTest() throws Exception{
		Price prodPrice1= new Price();
		prodPrice1.setId(MOVIE_ID);
		prodPrice1.setCurrencyCode("Rupee");
		prodPrice1.setPrice(new BigDecimal(150));
		Product prodDetails1= new Product(MOVIE_ID,"The Big Lebowski (Blu-ray)",prodPrice1);
		
		Mockito.when(productInfoService.updateProductInfo(MOVIE_ID, prodDetails1)).thenReturn(prodDetails1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/product/"+MOVIE_ID)
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"id\":"+MOVIE_ID+",\"name\":\"The Big Lebowski (Blu-ray)\",\"productPrice\":{\"price\":150,\"currencyCode\":\"Rupee\"}}")
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.CREATED.value(),result.getResponse().getStatus());
	}
}