package com.lti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lti.model.Admin;
import com.lti.model.Retailer;
import com.lti.model.User;

@Repository
public class AdminRetailerDaoImpl implements AdminRetailerDao {

	@PersistenceContext
	EntityManager em;

	/* Retailer should have address and admin */
	@Transactional
	public int addOrUpdateRetailer(Retailer retailer) {
		Retailer persistedRetailer = em.merge(retailer);
		return persistedRetailer.getRetailerId();
	}

	public Retailer findARetailer(int retailerId) {
		return em.find(Retailer.class, retailerId);
	}

	public List<Retailer> viewAllRetailers() {
		TypedQuery<Retailer> query = em.createQuery("select r from Retailer r", Retailer.class);
		return query.getResultList();
	}
	
	public List<Retailer> viewUnapprovedRetailers() {
		TypedQuery<Retailer> query = em.createQuery("select r from Retailer r where r.isEnabled=0", Retailer.class);
		return query.getResultList();
	}

	public int findByEmailAndPassword(String email, String password) {
		return (Integer) em
                .createQuery("select r.retailerId from Retailer r where r.retailerEmail = :em and r.retailerPassword = :pw")
                .setParameter("em", email)
                .setParameter("pw", password)
                .getSingleResult();
	}

	public boolean isRetailerPresent(String email) {
		return (Long) em
                .createQuery("select count(r.retailerId) from Retailer r where r.retailerEmail = :em")
				.setParameter("em", email).getSingleResult() == 1 ? true : false;
	}
	
	@Transactional
	public int findByEmailAndUpdate(String retailerEmail, String password) {
		Retailer retailer=(Retailer) em
                .createQuery("select r from Retailer r where r.retailerEmail =:rem")
                .setParameter("rem", retailerEmail)
                .getSingleResult();
		
		retailer.setRetailerPassword(password);
		Retailer persistedRetailer=em.merge(retailer);
		return persistedRetailer.getRetailerId();
	}
	
	public boolean isRetailerPresentAndApproved(String email) {
		return (Long) em
                .createQuery("select count(r.retailerId) from Retailer r where r.retailerEmail = :em and r.isEnabled=1")
				.setParameter("em", email).getSingleResult() == 1 ? true : false;
	}

	@Transactional
	public void enableRetailer(int retailerId) {
		String jpql = "update Retailer r set r.isEnabled=true where r.retailerId=:retid";
		Query query = em.createQuery(jpql);
		query.setParameter("retid", retailerId);
		
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void deleteRetailer(int retailerId) {
		String jpql = "delete from Retailer r where r.retailerId=:retid";
		Query query = em.createQuery(jpql);
		query.setParameter("retid", retailerId);
		
		query.executeUpdate();
	}
	
	public int findAdminByEmailAndPassword(String email, String password){
		return (Integer) em
	                .createQuery("select a.adminId from Admin a where a.adminEmail = :em and a.adminPassword = :pw")
	                .setParameter("em", email)
	                .setParameter("pw", password)
	                .getSingleResult();
	}


	public Admin findAdmin(int adminId){
		return em.find(Admin.class, adminId);
	}
}
