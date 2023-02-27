package com.lti.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lti.dao.AdminRetailerDao;
import com.lti.exception.RetailerServiceException;
import com.lti.exception.UserServiceException;
import com.lti.model.Admin;
import com.lti.model.Retailer;


@Service
public class AdminRetailerServiceImpl implements AdminRetailerService {
	
	@Autowired
	AdminRetailerDao adminRetailerDao;
	
	@Autowired
	private EmailService emailService;

	public void addOrUpdateRetailer(Retailer retailer) {
		
		if(!adminRetailerDao.isRetailerPresent(retailer.getRetailerEmail())) {
            int id=adminRetailerDao.addOrUpdateRetailer(retailer);
            String text="Retailer successfully registered. Your id is "+id+". Please wait for admin approval."
            		+ "We'll notify you once you get approved.";
            String subject="Registration Confirmation";
            emailService.sendEmail(retailer.getRetailerEmail(), text, subject);
        }
        else
            throw new RetailerServiceException("Retailer already registered!");
	}

	public Retailer findARetailer(int retailerId) {
		
		return adminRetailerDao.findARetailer(retailerId);
	}

	public List<Retailer> viewAllRetailers() {
		
		return adminRetailerDao.viewAllRetailers();
	}
	
	public List<Retailer> viewUnapprovedRetailers() {
		
		return adminRetailerDao.viewUnapprovedRetailers();
	}

	public Retailer loginRetailer(String email, String password) {
		try {
            if(!adminRetailerDao.isRetailerPresentAndApproved(email))
                throw new RetailerServiceException("Retailer not registered/approved!");
            int retailerId = adminRetailerDao.findByEmailAndPassword(email, password);
            Retailer customer = adminRetailerDao.findARetailer(retailerId);
            return customer;
        }
        catch(EmptyResultDataAccessException e) {
            throw new RetailerServiceException("Incorrect email/password");
        }
	}

	public void enableRetailer(int retailerId) {
		adminRetailerDao.enableRetailer(retailerId);
		Retailer retailer=adminRetailerDao.findARetailer(retailerId);
		String text="Retailer with retailerId = "+retailerId+" has been approved by FlyBuy admin.";
        String subject="Retailer Approved!";
        emailService.sendEmail(retailer.getRetailerEmail(), text, subject);
	}
	
	public String resetRetailerPassword(String email) {
		if(adminRetailerDao.isRetailerPresent(email)) {
			int length=8;
			boolean useLetters = true;
		    boolean useNumbers = true;
		    String generatedPassword = RandomStringUtils.random(length, useLetters, useNumbers);
		    String text="Your new system generated password is "+generatedPassword+". Please change your"
		    		+ " password on FlyBuy.";
	        String subject="FlyBuy Password Reset";
		    emailService.sendEmail(email, text, subject);
		    return generatedPassword;
		}
		else {
			throw new RetailerServiceException("Email not registered!");
		}
	}
	
	public int findByEmailAndUpdate(String email, String password) {
		return adminRetailerDao.findByEmailAndUpdate(email, password);
	}

	public void deleteRetailer(int retailerId) {
		adminRetailerDao.deleteRetailer(retailerId);
	}
	
	public Admin loginAdmin(String email, String password){
		try{
			int retailerId = adminRetailerDao.findAdminByEmailAndPassword(email, password);
			Admin admin = adminRetailerDao.findAdmin(retailerId);
			return admin;
		}catch(EmptyResultDataAccessException e){
			throw new RetailerServiceException("Incorrect email/password");
		}
	}
}
