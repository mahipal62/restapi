package com.myretail.productdetail.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.myretail.productdetail.model.Price;




@Repository
public interface ProductPriceRepository extends MongoRepository<Price,Integer>{
	
	
	public Price findById(int productId);
	
	

}
