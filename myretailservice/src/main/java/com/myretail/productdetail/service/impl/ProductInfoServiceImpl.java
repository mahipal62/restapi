package com.myretail.productdetail.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.mongodb.MongoException;
import com.myretail.productdetail.gateway.TargetProductAPIGateway;
import com.myretail.productdetail.model.Price;
import com.myretail.productdetail.model.Product;
import com.myretail.productdetail.repository.ProductPriceRepository;
import com.myretail.productdetail.service.ProductInfoService;


@Service
public class ProductInfoServiceImpl implements ProductInfoService{
	private final Logger log = Logger.getLogger(ProductInfoServiceImpl.class.getName());
	

	
	@Autowired ProductPriceRepository productPriceRepository;
	
	@Autowired
	TargetProductAPIGateway gateway;
	
	public Product getProductInfo(int id) throws  MongoException, HttpClientErrorException{
		log.info("in  getProductDetails ");
		log.debug("id: "+id);
		String productName = null;
		try {
			productName = gateway.getProductName(id);
		} catch (Exception e) {
		log.error("the product is not available in the Target Rest API"+id);
		}
		log.debug("productName: "+productName);
		Price prodPrice=getProductPrice(id);
		
		if(prodPrice==null){
			log.error("product price is not available");
			throw new MongoException("price details for product with id="+id+" not found in mongo db for collection productprice");
		}
		Product prodDetails= new Product(id,productName,prodPrice);
		log.debug("prodDetails: "+prodDetails);
		return prodDetails;	
	}
	
	
	

	public Product updateProductInfo(int id, Product newProduct) throws Exception {
		log.info("Method putProductDetails begin");
		log.debug(" newProduct : "+newProduct);
		if(id!=newProduct.getId()){
			throw new Exception(" Product price cannot be updated, request body json should have matching id with path variable.");
		}
		Price newProductPrice=newProduct.getPrice();
		if(newProduct.getPrice().getCurrencyCode()==null||newProduct.getPrice().getPrice()==null){
				throw new Exception(" Please check product price and currency code details, it should not be empty ");
		}
		newProductPrice.setId(id);
		String productName=gateway.getProductName(id);
		newProduct.setName(productName);
		newProductPrice=updateProductPrice(id,newProduct);
		
		newProduct.setPrice(newProductPrice);
		log.info("Method putProductDetails end");
		return newProduct;
	}
	
	
	
	
	public Price getProductPrice(int productId) throws MongoException{
		log.info("Method getProductPrice begin");
		log.debug("id : "+productId);
		Price prodPrice=productPriceRepository.findById(productId);
		log.debug("prodPrice : "+prodPrice);
		return prodPrice;
	}
	
	public Price updateProductPrice(int prodductId,Product product) throws MongoException{
		log.info("updateProductPrice Enter");
		Price newProductPrice=product.getPrice();
		newProductPrice.setId(prodductId);
		if(getProductPrice(prodductId)!=null){
			newProductPrice=productPriceRepository.save(product.getPrice());
		}else{
			log.error("price detail null mongo exception thrown");
			throw new MongoException("price details for product with id="+prodductId+" not found in mongo db for collection 'productprice'");
		}
		log.debug("newProductPrice : "+newProductPrice);
		log.info("Method getProductPrice end");
		return newProductPrice;
	}
}
