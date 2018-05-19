package com.myretail.productdetail.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.mongodb.MongoException;
import com.myretail.productdetail.aspect.AdderAround;
import com.myretail.productdetail.aspect.LogExecutionTime;
import com.myretail.productdetail.exception.ProductNotFoundException;
import com.myretail.productdetail.gateway.TargetProductAPIGateway;
import com.myretail.productdetail.model.Price;
import com.myretail.productdetail.model.Product;
import com.myretail.productdetail.repository.ProductPriceRepository;
import com.myretail.productdetail.service.ProductInfoService;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
	private final Logger log = Logger.getLogger(ProductInfoServiceImpl.class.getName());

	@Autowired
	ProductPriceRepository productPriceRepository;

	@Autowired
	TargetProductAPIGateway gateway;

	@LogExecutionTime
	@AdderAround
	public Product getProductInfo(int id) throws ProductNotFoundException, HttpClientErrorException {
		String productName = null;
		Price prodPrice = null;
		try {
			productName = gateway.getProductName(id);
		} catch (Exception e) {
			log.error("the product is not available in the Target Rest API" + id);
			if (e instanceof HttpClientErrorException) {
				throw new HttpClientErrorException(((HttpClientErrorException) e).getStatusCode(),"the product is not available in the Target Rest API=" + id);
			}
			throw new ProductNotFoundException("the product is not available in the Target Rest API " + id);
		}

		try {
			prodPrice = getProductPrice(id);

			if (prodPrice == null) {
				log.error("product price is not available");
				throw new ProductNotFoundException("price details for product with id=" + id + " not found");
			}
		} catch (Exception e) {
			throw new ProductNotFoundException("price details for product with id=" + id + " not found");
		}

		Product prodDetails = new Product(id, productName, prodPrice);
		return prodDetails;
	}

	@LogExecutionTime
	@AdderAround
	public Product updateProductInfo(int id, Product newProduct) throws Exception {
		if (id != newProduct.getId()) {
			throw new Exception(
					" Product price cannot be updated, request body json should have matching id with path variable.");
		}
		Price newProductPrice = newProduct.getPrice();
		if (newProduct.getPrice().getCurrencyCode() == null || newProduct.getPrice().getPrice() == null) {
			throw new Exception(" Please check product price and currency code details, it should not be empty ");
		}
		newProductPrice.setId(id);
		newProductPrice = updateProductPrice(id, newProduct);
		String productName = gateway.getProductName(id);
		newProduct.setName(productName);
		newProduct.setPrice(newProductPrice);
		return newProduct;
	}

	public Price getProductPrice(int productId) throws MongoException {
		Price prodPrice = productPriceRepository.findById(productId);
		return prodPrice;
	}

	@AdderAround
	public Price updateProductPrice(int prodductId, Product product) throws ProductNotFoundException {
		Price newProductPrice = product.getPrice();
		newProductPrice.setId(prodductId);
		if (getProductPrice(prodductId) != null) {
			newProductPrice = productPriceRepository.save(product.getPrice());
		} else {
			log.error("price detail null mongo exception thrown");
			throw new ProductNotFoundException("price details cannot be updated because the details for the product with id=" + prodductId+ " not found in mongo db");
		}
		return newProductPrice;
	}
}
