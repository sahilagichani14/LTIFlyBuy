package com.lti.service;

import java.util.List;

import com.lti.model.Category;

public interface CategoryService {

	public int addACategory(Category category);
	public Category findACategory(int categoryID);
	public List<Category> viewAllCategories();
}
