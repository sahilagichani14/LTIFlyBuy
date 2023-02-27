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

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public int addACategory(Category category)
	{
		Category persistedCategory=em.merge(category);
		return persistedCategory.getCategoryId();
	}
	public Category findACategory(int categoryID){
		return em.find(Category.class, categoryID);
	}
	
	public List<Category> viewAllCategories(){
		String jpql= "select c from Category c";
		TypedQuery<Category> query = em.createQuery(jpql, Category.class);
		return query.getResultList();
	}
}
