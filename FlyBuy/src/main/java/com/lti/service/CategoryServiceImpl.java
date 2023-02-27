package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dao.CategoryDao;
import com.lti.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryDao categoryDao;

	public int addACategory(Category category) {
		
		return categoryDao.addACategory(category);
	}

	public Category findACategory(int categoryID) {
		
		return categoryDao.findACategory(categoryID);
	}

	public List<Category> viewAllCategories() {
		
		return categoryDao.viewAllCategories();
	}

}
