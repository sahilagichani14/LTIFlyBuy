package com.lti.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="tbl_category")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products"})
public class Category {
	
	
	@Id
	@SequenceGenerator(name = "catSeq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catSeq")
	int categoryId;
	String categoryName;
	
	@JsonIgnore
	@OneToMany(mappedBy="category",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	List<Product> products;
	
	@JsonIgnore
	public List<Product> getProduct() {
		return products;
	}
	public void setProduct(List<Product> products) {
		this.products = products;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}

