package com.lti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_address_retailer")
@SequenceGenerator(name = "addrRetailSeq", initialValue = 201, allocationSize = 1)
public class AddressRetailer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addrRetailSeq")
	private int id;
	private String street;
	private String city;
	private String state;
	
	@OneToOne
	@JoinColumn(name = "retailer_Id")
	private Retailer retailer;
	
	public Retailer getRetailer() {
		return retailer;
	}
	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
