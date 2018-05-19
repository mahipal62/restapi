package com.myretail.productdetail.gateway.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.productdetail.aspect.AdderAround;
import com.myretail.productdetail.aspect.LogExecutionTime;
import com.myretail.productdetail.constant.ProductInfoServiceConstant;
import com.myretail.productdetail.gateway.TargetProductAPIGateway;

@Component
public class TargetProductAPIGatewayImpl implements TargetProductAPIGateway {
	private final Logger log = Logger.getLogger(TargetProductAPIGatewayImpl.class.getName());
	@Autowired
	RestTemplate template;
	
	@Autowired
    private Environment environment;
	
	 @LogExecutionTime
	 @AdderAround
	public String getProductName(int producId) throws Exception {
		 String productName="";
		 try {
		ResponseEntity<String> response= template.getForEntity(getServiceUrl(producId), String.class);
			ObjectMapper mapper = new ObjectMapper();
				JsonNode root=null;
				String jsonString=response.getBody();
				if(jsonString!=null||!ProductInfoServiceConstant.SPACE.equals(jsonString)){
					root = mapper.readTree(jsonString);
					if(root.findValue(ProductInfoServiceConstant.PRODUCT)!=null){
						root=root.findValue(ProductInfoServiceConstant.PRODUCT);
						if(root.findValue(ProductInfoServiceConstant.ITEM)!=null){
							root=root.findValue(ProductInfoServiceConstant.ITEM);
							if(root.findValue(ProductInfoServiceConstant.DESC)!=null){
								root=root.findValue(ProductInfoServiceConstant.DESC);
								if(root.findValue(ProductInfoServiceConstant.TITLE)!=null){
									productName=root.findValue(ProductInfoServiceConstant.TITLE).asText();
								}
							}
						}
					}
				}
			} 
			catch (Exception e) {
				log.error("Error Occured during getting the data from the Target API "+e.getMessage());
				throw new Exception(e);
			}
			return productName;
		}

	private String getServiceUrl(int producId) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(environment.getProperty(ProductInfoServiceConstant.TARGET_API_URL));
		builder.append(producId);
		builder.append(environment.getProperty(ProductInfoServiceConstant.TARGET_API_URL_PARAM));
		return builder.toString();
	}
	
	


}
