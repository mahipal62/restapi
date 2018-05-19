package com.myretail.productdetail.controller;


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

import com.myretail.productdetail.aspect.AdderAround;
import com.myretail.productdetail.aspect.LogExecutionTime;
import com.myretail.productdetail.exception.ProductNotFoundException;
import com.myretail.productdetail.model.Product;
import com.myretail.productdetail.service.ProductInfoService;

@Api (value="MyRetailAPI")
@RestController
@RequestMapping("/")
public class ProductController {
	
	
	@Autowired
	private ProductInfoService service;
	
	
	
	
	/**
     * Gets productName from Target API service and pricing information from MongoDB NoSQL
     * database and gives out a JSON response.
     * @param productId Id of product we need information about.
     * @return
     * @throws HttpClientErrorException,ProductNotFoundException
     */
   @ApiOperation(value = "Gets the product infromation by product id")
   @ApiResponses(value = {@ApiResponse(code = 200, message = "Success response"), @ApiResponse(code = 404, message = "Product not found")})
   @RequestMapping(value="product/{id}",method=RequestMethod.GET)
   @LogExecutionTime
   @AdderAround
	public Product getProductDetails(@PathVariable int id) throws HttpClientErrorException, ProductNotFoundException{
		Product prodDetail=null;
		prodDetail=service.getProductInfo(id);
		return prodDetail;
	}
	/**
     * updates pricing information in NoSQL database.
     * @param prodInfo Product info JSON request body
     * @param productId Id of product that need to be stored.
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "Modifies the product information")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "ProductId in request header and body doesn't match")})
	@RequestMapping(value="product/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    @LogExecutionTime
    @AdderAround
	public Product updateProduct(@PathVariable int id,@RequestBody Product product) throws Exception{
		Product updatedProductDetails=service.updateProductInfo(id, product);
		return updatedProductDetails;
	}

}
