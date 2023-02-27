package com.lti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.EmailStatus;
import com.lti.dto.LoginDto;
import com.lti.dto.LoginStatus;
import com.lti.dto.RetailerLoginStatusDto;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusType;
import com.lti.exception.RetailerServiceException;
import com.lti.exception.UserServiceException;
import com.lti.model.Admin;
import com.lti.model.Retailer;
import com.lti.service.AdminRetailerService;

@RestController
@CrossOrigin
public class AdminRetailerController {

	@Autowired
	AdminRetailerService adminRetailerService;

	@PostMapping(path = "/retailer-register")
	public Status addOrUpdateRetailer(@RequestBody Retailer retailer) {

		try {
			adminRetailerService.addOrUpdateRetailer(retailer);
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Retailer updated successfully");
			return status;
		} catch (RetailerServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}

	}

	@PostMapping("/retailer-login")
	public RetailerLoginStatusDto loginRetailer(@RequestBody LoginDto loginDto) {
		try {
			Retailer retailer = adminRetailerService.loginRetailer(loginDto.getEmail(), loginDto.getPassword());
			RetailerLoginStatusDto loginStatus = new RetailerLoginStatusDto();
			loginStatus.setStatus(StatusType.SUCCESS);
			loginStatus.setMessage("Retailer logged in successfully");
			loginStatus.setRetailerId(retailer.getRetailerId());
			loginStatus.setRetailerName(retailer.getRetailerName());
			loginStatus.setEnabled(retailer.isEnabled());
			return loginStatus;
		} catch (RetailerServiceException e) {
			RetailerLoginStatusDto loginStatus = new RetailerLoginStatusDto();
			loginStatus.setStatus(StatusType.FAILURE);
			loginStatus.setMessage(e.getMessage());
			return loginStatus;
		}
	}
	
	@PostMapping("/find-retailer")
	public Retailer findARetailer(@RequestParam("retailerId") int retailerId) {

		return adminRetailerService.findARetailer(retailerId);
	}
	
	@GetMapping("/view-all-retailers")
	public List<Retailer> viewAllRetailers() {

		return adminRetailerService.viewAllRetailers();
	}
	
	
	@GetMapping("/enable-retailer")
	public Status enableRetailer(@RequestParam("retailerId") int retailerId) {
		
		try {
			adminRetailerService.enableRetailer(retailerId);
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Retailer enabled!");
			return status;
		} catch (RetailerServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Failed");
			return status;
		}
	}
	
	@GetMapping("/view-unapproved-retailers")
	public List<Retailer> viewUnapprovedRetailers() {
		return adminRetailerService.viewUnapprovedRetailers();
	}
	
	@GetMapping("/delete-retailer")
	public Status deleteRetailer(@RequestParam("retailerId") int retailerId) {
		try {
			adminRetailerService.deleteRetailer(retailerId);
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Retailer deleted!");
			return status;
		} catch (RetailerServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage("Failed");
			return status;
		}
	}
	
	@GetMapping(path = "/check-retailer-email")
	public EmailStatus resetUserPassword(@RequestParam("emailId") String email) {
		try {
			String generatedPassword=adminRetailerService.resetRetailerPassword(email);
			EmailStatus emailStatus=new EmailStatus();
			emailStatus.setStatus(StatusType.SUCCESS);
			emailStatus.setMessage("Email successful!");
			emailStatus.setGeneratedPassword(generatedPassword);
			return emailStatus;
		}
		catch(UserServiceException e) {
			EmailStatus emailStatus=new EmailStatus();
			emailStatus.setStatus(StatusType.FAILURE);
			emailStatus.setMessage(e.getMessage());
			return emailStatus;
		}
	}
	
	@GetMapping(path = "/reset-retailer-password")
	public int findByEmailAndUpdate(@RequestParam("emailId") String email, @RequestParam("password") String password) {
		return adminRetailerService.findByEmailAndUpdate(email, password);
	}
	
	@PostMapping("/admin-login")
	public Status loginAdmin(@RequestBody LoginDto loginDto) {
		try{
			Admin admin = adminRetailerService.loginAdmin(loginDto.getEmail(), loginDto.getPassword());
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Admin Logged in Successfully!");
			return status;
		}catch(RetailerServiceException e){
			Status status = new Status();
			status.setMessage("Admin Login Failed");
			status.setStatus(StatusType.FAILURE);
			return status;
		}
	}
}
