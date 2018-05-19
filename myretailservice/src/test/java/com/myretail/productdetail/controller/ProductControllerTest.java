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
    private static final int PRODUCT_ID =13860428;
    private static final String CURRENCY_US="USD";
    private static final String CURRENCY_UK="EURO";
    private static final String PRODUCT_NAME="The Big Lebowski (Blu-ray)";
    private static final String EXPECTED_JSON_STR_US="{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"price\":{\"price\":100,\"currencyCode\":\"USD\"}}";
    private static final String PRODUCT_ID_TEST="TEST";
    private static final String UPDATED_JSON_STR_UK="{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"productPrice\":{\"price\":150,\"currencyCode\":\"EURO\"}}";

	Product prodDetail=null;
	Price prodPrice= new Price();
	@Before
	public void setup() {
		prodPrice.setId(PRODUCT_ID);
		prodPrice.setCurrencyCode(CURRENCY_US);
		prodPrice.setPrice(new BigDecimal(100));
		prodDetail= new Product(PRODUCT_ID,PRODUCT_NAME,prodPrice);
	}
	
	
	@Test
	public void getProductDetailsTest() throws Exception {
		Mockito.when(productInfoService.getProductInfo(PRODUCT_ID)).thenReturn(prodDetail);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/product/"+PRODUCT_ID).accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = EXPECTED_JSON_STR_US;
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test(expected = MethodArgumentTypeMismatchException.class)
	public void getProductDetailsInvalidRequestTest() throws Exception,MethodArgumentTypeMismatchException {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/"+PRODUCT_ID_TEST).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());
			throw 	result.getResolvedException();
	}
	
	@Test
	public void updateProductDetailsTest() throws Exception{
		Price prodPriceNew= new Price();
		prodPriceNew.setId(PRODUCT_ID);
		prodPriceNew.setCurrencyCode(CURRENCY_UK);
		prodPriceNew.setPrice(new BigDecimal(150));
		Product prodDetails1= new Product(PRODUCT_ID,PRODUCT_NAME,prodPriceNew);
		
		Mockito.when(productInfoService.updateProductInfo(PRODUCT_ID, prodDetails1)).thenReturn(prodDetails1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/product/"+PRODUCT_ID)
				.accept(MediaType.APPLICATION_JSON)
				.content(UPDATED_JSON_STR_UK)
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.CREATED.value(),result.getResponse().getStatus());
	}
}