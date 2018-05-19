package com.myretail.productdetail;


import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.myretail.productdetail.model.Price;
import com.myretail.productdetail.repository.ProductPriceRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2 
public class MyRetailApp {
	
	@Autowired
	ProductPriceRepository repository;

		public static void main(String[] args) {
			SpringApplication.run(MyRetailApp.class, args);
		}

		@Bean
		public RestTemplate restTemplate() {
		    return new RestTemplate();
		}
		
		@PostConstruct
	    public void storeSampleData() {
	     
	        repository.save(new Price(13860428, new BigDecimal(13.49).setScale(2, BigDecimal.ROUND_UP), "USD" ));
	        repository.save(new Price(15117729, new BigDecimal(25.22).setScale(2, BigDecimal.ROUND_UP), "USD" ));
	        repository.save(new Price(16483589, new BigDecimal(15.24).setScale(2, BigDecimal.ROUND_UP), "USD" ));
	        repository.save(new Price(16696652, new BigDecimal(24.50).setScale(2, BigDecimal.ROUND_UP), "USD" ));
	        repository.save(new Price(16752456, new BigDecimal(24.50).setScale(2, BigDecimal.ROUND_UP), "USD" ));
	        repository.save(new Price(15643793, new BigDecimal(24.50).setScale(2, BigDecimal.ROUND_UP), "USD" ));
	        
	    }
		
	
		
	}


