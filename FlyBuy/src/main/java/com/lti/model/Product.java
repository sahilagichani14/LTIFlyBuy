package com.lti.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_product")
public class Product {
	@Id
	@SequenceGenerator(name = "productSeq", initialValue = 101, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSeq")
	int productId;
	String productName;
	String productDescription;
	double productPrice;
	int productQuantity;
	String productManufacturerName;
	String imageUrl;
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	List<Item> items;
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id")
	Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="retailer_id")
	Retailer retailer;
	
	
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public int getProductId() {
		return productId;
	}
	

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
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
	public String getProductManufacturerName() {
		return productManufacturerName;
	}
	public void setProductManufacturerName(String productManufacturerName) {
		this.productManufacturerName = productManufacturerName;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productDescription="
				+ productDescription + ", productPrice=" + productPrice + ", productQuantity=" + productQuantity
				+ ", productManufacturerName=" + productManufacturerName + ", imageUrl=" + imageUrl + ", category="
				+ category + ", retailer=" + retailer + "]";
	}
}
