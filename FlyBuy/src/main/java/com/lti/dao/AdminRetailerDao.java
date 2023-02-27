package com.lti.dao;

import java.util.List;

import com.lti.model.Admin;
import com.lti.model.Retailer;

public interface AdminRetailerDao {

	public int addOrUpdateRetailer(Retailer retailer);
	public Retailer findARetailer(int retailerId);
	public List<Retailer> viewAllRetailers();
	public int findByEmailAndPassword(String email, String password);
	public boolean isRetailerPresent(String email);
	public void enableRetailer(int retailerId);
	public List<Retailer> viewUnapprovedRetailers();
	public boolean isRetailerPresentAndApproved(String email);
	public int findByEmailAndUpdate(String retailerEmail, String password);
	public void deleteRetailer(int retailerId);
	public int findAdminByEmailAndPassword(String email, String password);
	public Admin findAdmin(int adminId);
}