package com.myretail.productdetail.service;


import org.springframework.web.client.HttpClientErrorException;

import com.myretail.productdetail.exception.ProductNotFoundException;
import com.myretail.productdetail.model.Product;

public interface ProductInfoService {
	
	public Product getProductInfo(int id) throws ProductNotFoundException, HttpClientErrorException;
	public Product updateProductInfo(int id,Product newProduct) throws Exception;

}
