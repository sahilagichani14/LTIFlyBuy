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

import com.lti.dto.Status;
import com.lti.dto.Status.StatusType;
import com.lti.model.Category;
import com.lti.service.CategoryService;

@RestController
@CrossOrigin
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@PostMapping(path="/add-category")
	public Status addACategory(@RequestBody Category category) {
		
		int id = categoryService.addACategory(category);
		Status status = new Status();
		status.setStatus(StatusType.SUCCESS);
		status.setMessage("Category Added successfully!");
		return status;
	}

	@GetMapping(path="/find-category")
	public Category findACategory(@RequestParam int categoryID) {
		
		return categoryService.findACategory(categoryID);
	}

	@GetMapping(path="/view-all-categories")
	public List<Category> viewAllCategories() {
		
		return categoryService.viewAllCategories();
	}
}
