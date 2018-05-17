package com.myretail.productdetail.gateway.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.productdetail.constant.ProductInfoServiceConstant;
import com.myretail.productdetail.gateway.TargetProductAPIGateway;

@Component
public class TargetProductAPIGatewayImpl implements TargetProductAPIGateway {
	private final Logger log = Logger.getLogger(TargetProductAPIGatewayImpl.class.getName());
	@Autowired
	RestTemplate template;
	
	@Autowired
    private Environment environment;
	public String getProductName(int producId) throws Exception {
		
		String url=	environment.getProperty("target.api.url")+producId+environment.getProperty("target.api.url.param");
			ResponseEntity<String> response= template.getForEntity(url, String.class);
			ObjectMapper mapper = new ObjectMapper();
			String productName="";
			try {
				JsonNode root=null;
				String jsonString=response.getBody();
				if(jsonString!=null||!"".equals(jsonString)){
					log.info(jsonString);
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
	
	


}
