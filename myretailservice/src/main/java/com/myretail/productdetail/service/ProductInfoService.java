package com.myretail.productdetail.service;


import org.springframework.web.client.HttpClientErrorException;

import com.mongodb.MongoException;
import com.myretail.productdetail.model.Product;

public interface ProductInfoService {
	
	public Product getProductInfo(int id) throws MongoException,MongoException, HttpClientErrorException;
	public Product updateProductInfo(int id,Product newProduct) throws Exception;

}
