package com.lti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.model.Category;
import com.lti.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public int addAProductWithCategory(Product product)
	{
		Product persistedProduct=em.merge(product);
		return persistedProduct.getProductId();
	}
	
	public Product findAProduct(int productId)
	{
		return em.find(Product.class, productId);
	}
	
	
	public List<Product> viewAllProducts(){
		String jpql= "select p from Product p";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		return query.getResultList();		
		
	}
	
	public List<Product> viewProductByCategory(String category){
		String jpql = "select p from Product p where p.category.categoryName=:catg";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("catg", category);
		
		return query.getResultList();
	}

	
	public List<Product> searchProductByDescription(String description) {
		String jpql = "select p from Product p where p.productDescription like '%'||:desc||'%'";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("desc", description);
		
		return query.getResultList();
	}

	public List<Product> viewProductByRetailer(int retailerId) {
		String jpql = "select p from Product p where p.retailer.retailerId=:retId";
		TypedQuery<Product> query = em.createQuery(jpql, Product.class);
		query.setParameter("retId", retailerId);
		for(Product p: query.getResultList()) {
			System.out.println(p);
		}
		return query.getResultList();
	}

	@Transactional
	public int updateProduct(Product product) {
		Product updatedProduct = em.merge(product);
		return updatedProduct.getProductId();
	}
	
}
