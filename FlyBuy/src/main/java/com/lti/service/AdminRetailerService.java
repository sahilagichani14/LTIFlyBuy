package com.lti.service;

import java.util.List;

import com.lti.model.Admin;
import com.lti.model.Retailer;

public interface AdminRetailerService {

	public void addOrUpdateRetailer(Retailer retailer);
	public Retailer findARetailer(int retailerId);
	public List<Retailer> viewAllRetailers();
	public Retailer loginRetailer(String email, String password);
	public void enableRetailer(int retailerId);
	public List<Retailer> viewUnapprovedRetailers();
	public void deleteRetailer(int retailerId);
	public String resetRetailerPassword(String email);
	public int findByEmailAndUpdate(String email, String password);
	public Admin loginAdmin(String email, String password);
}
