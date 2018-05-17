package com.myretail.productdetail.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import com.mongodb.MongoException;
import com.myretail.productdetail.model.Product;
import com.myretail.productdetail.service.ProductInfoService;

@Api (value="MyRetailAPI")
@RestController
@RequestMapping("/")
public class ProductController {
	
	private final Logger log = Logger.getLogger(ProductController.class.getName());
	
	@Autowired
	private ProductInfoService service;
	
	@ApiOperation(value = "Gets the product infromation by product id")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Success response"),
                    @ApiResponse(code = 404, message = "Product not found")})
	@RequestMapping(value="product/{id}",method=RequestMethod.GET)
	/**
     * Gets productName from Target API service and pricing information from MongoDB NoSQL
     * database and gives out a JSON response.
     * @param productId Id of product we need information about.
     * @return
     * @throws ProductNotFoundException
     */
	public Product getProductDetails(@PathVariable int id) throws HttpClientErrorException, MongoException{
		log.info("in controller getProductDetails id :"+id);
		Product prodDetail=null;
		prodDetail=service.getProductInfo(id);
		log.info(" return productDetails :"+prodDetail);
		return prodDetail;
	}
	/**
     * updates pricing info in NoSQL database.
     * @param prodInfo Product info JSON request body
     * @param productId Id of product that need to be stored.
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "Modifies the product information")
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "Created"),
                    @ApiResponse(code = 400, message = "ProductId in request header and body doesn't match")})
	@RequestMapping(value="product/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
	public Product updateProduct(@PathVariable int id,@RequestBody Product product) throws Exception{
		log.info("in controller putProductDetails id :"+id);
		log.info("in controller putProductDetails requestBody :"+product);
		Product updatedProductDetails=service.updateProductInfo(id, product);
		log.info(" updated putProductDetails :"+updatedProductDetails);
		return updatedProductDetails;
	}

}
