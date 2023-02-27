package com.lti.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_admin")
@SequenceGenerator(name = "adminSeq", initialValue = 1, allocationSize = 1)
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminSeq")
	private int adminId;
	private String adminName;
	private String adminEmail;
	private String adminPassword;

	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
	private List<Retailer> retailers;

	public List<Retailer> getRetailers() {
		return retailers;
	}

	public void setRetailers(List<Retailer> retailers) {
		this.retailers = retailers;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	
}
