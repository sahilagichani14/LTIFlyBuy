package com.lti.dto;

import org.springframework.web.multipart.MultipartFile;

public class RetailerProductDto {

	int productId;
	String productName;
	String productDescription;
	double productPrice;
	int productQuantity;
	String productManufacturerName;
	String categoryName;
	String retailerName;
	int retailerId;
	int categoryId;
	String imageUrl;
	

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductManufacturerName() {
		return productManufacturerName;
	}

	public void setProductManufacturerName(String productManufacturerName) {
		this.productManufacturerName = productManufacturerName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}

	public int getRetailerId() {
		return retailerId;
	}
		                                                                  
	public void setRetailerId(int retailerId) {
		this.retailerId = retailerId;
	}
}
