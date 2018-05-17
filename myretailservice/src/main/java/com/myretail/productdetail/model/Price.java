package com.myretail.productdetail.model;
import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="price")
public class Price implements Serializable{

	
	private static final long serialVersionUID = -8331537878236316444L;
	
	@Id
	private int id;
	private BigDecimal price;
	private String currencyCode;
	
	
	public Price(int id, BigDecimal price, String currencyCode) {
		super();
		this.id = id;
		this.price = price;
		this.currencyCode = currencyCode;
	}
	
	
	public Price(){
	}


	@JsonIgnore
	@JsonProperty(value = "id")
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "current_price {"
			+ "price=" + price + ","
			+ "currencyCode =" + currencyCode + "}";
	}





	
}