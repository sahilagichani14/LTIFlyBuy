package com.lti.dao;

import java.util.List;

import com.lti.model.Category;

public interface CategoryDao {

	public int addACategory(Category category);
	public Category findACategory(int categoryID);
	public List<Category> viewAllCategories();
}