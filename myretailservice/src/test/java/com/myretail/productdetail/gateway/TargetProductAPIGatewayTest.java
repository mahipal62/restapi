package com.myretail.productdetail.gateway;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.myretail.productdetail.constant.ProductInfoServiceConstant;
import com.myretail.productdetail.gateway.impl.TargetProductAPIGatewayImpl;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes ={ TargetProductAPIGatewayImpl.class,RestTemplate.class})
public class TargetProductAPIGatewayTest {

	@Autowired
	TargetProductAPIGateway gateway;
	
	@MockBean
	Environment environment;
	
	
	 private static final String PRODUCT_NAME="The Big Lebowski (Blu-ray)";
	 
	 
	
	
	@Test
	public void testGetProductName() throws Exception {
		Mockito.when(environment.getProperty(ProductInfoServiceConstant.TARGET_API_URL)).thenReturn("https://redsky.target.com/v1/pdp/tcin/");
		Mockito.when(environment.getProperty(ProductInfoServiceConstant.TARGET_API_URL_PARAM)).thenReturn("?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics");
		String productName = gateway.getProductName(13860428);
		
		
		Assert.assertEquals(PRODUCT_NAME, productName);
		
		
		
	}
	
	@Test(expected = Exception.class)
	public void testGetProductNameException() throws Exception {
		Mockito.when(environment.getProperty(ProductInfoServiceConstant.TARGET_API_URL)).thenReturn("https://redsky.target.com/v1/pdp/tcin/");
		Mockito.when(environment.getProperty(ProductInfoServiceConstant.TARGET_API_URL_PARAM)).thenReturn("?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics");
		gateway.getProductName(13860427);
		

		
		
		
	}

}
