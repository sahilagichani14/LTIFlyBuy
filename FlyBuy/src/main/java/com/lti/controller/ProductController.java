package com.lti.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.ProductCategoryDto;
import com.lti.dto.ProductDto;
import com.lti.dto.ProductImageDto;
import com.lti.dto.RetailerProductDto;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusType;
import com.lti.exception.ProductServiceException;
import com.lti.model.Category;
import com.lti.model.Product;
import com.lti.model.Retailer;
import com.lti.service.AdminRetailerService;
import com.lti.service.CategoryService;
import com.lti.service.ProductService;

@RestController
@CrossOrigin
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AdminRetailerService retailerService;

	
	
	/*@PostMapping(path="/add-product")
	public Status addAProductWithCategory(@RequestBody Product product) {

			int id= productService.addAProductWithCategory(product);
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Product Added successfully!");
			return status;
	}*/
	
	@PostMapping(path="/add-product")
	public Status addAProductWithCategory(ProductDto productDto) {
		Product product = new Product();
		product.setProductName(productDto.getProductName());
		product.setProductDescription(productDto.getProductDescription());
		product.setProductManufacturerName(productDto.getProductManufacturerName());
		product.setProductQuantity(productDto.getProductQuantity());
		product.setProductPrice(productDto.getProductPrice());
		
		Category category = categoryService.findACategory(Integer.parseInt(productDto.getCategoryId()));
		Retailer retailer = retailerService.findARetailer(Integer.parseInt(productDto.getRetailerId()));
		
		product.setCategory(category);
		product.setRetailer(retailer);
		
		String imageUploadLocation="d:/productimages/";			// create "productimages" folder in d drive
		String filename = productDto.getProductImg().getOriginalFilename();
		String targetFile = imageUploadLocation+filename;
		product.setImageUrl(filename);		
		
		int id= productService.addAProductWithCategory(product);
		
		
		try {
			FileCopyUtils.copy(productDto.getProductImg().getInputStream(), new FileOutputStream(targetFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		} catch (IOException e) {
			e.printStackTrace();
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
		
		
		Status productStatus = new Status();
		productStatus.setStatus(StatusType.SUCCESS);
		productStatus.setMessage("Uploaded!");
		return productStatus;
	}
	
	
	
	@GetMapping(path="/find-product")
	public Product findAProduct(@RequestParam int productId) {
		
		return productService.findAProduct(productId);
	}

	@GetMapping(path="/view-all-products")
	public List<Product> viewAllProducts() {
		return productService.viewAllProducts();
	}

	/*@GetMapping(path="/view-products-by-category")//ask sir
	public List<Product> viewProductByCategory(@RequestParam String category) {
		return productService.viewProductByCategory(category);
	}*/
	
	@GetMapping(path="/view-products-by-category")
	public List<ProductCategoryDto> viewProductByCategory(@RequestParam("category") String category, HttpServletRequest request) {
		List<Product> productsList = productService.viewProductByCategory(category);
		List<ProductCategoryDto> productDtoList = new ArrayList();
		
		String projPath = request.getServletContext().getRealPath("/");
		String tempDownloadPath = projPath+"/downloads/";
		
		for(Product p:productsList) {
			File f = new File(tempDownloadPath);
			if(!f.exists()) {
				f.mkdir();
			}
			String targetFile = tempDownloadPath + p.getImageUrl();
			String uploadedImagePath = "d:/productimages/";
			String sourceFile = uploadedImagePath + p.getImageUrl();
			
			try {
				FileCopyUtils.copy(new File(sourceFile), new File(targetFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		for(Product p:productsList) {
			ProductCategoryDto pd = new ProductCategoryDto();
			pd.setProductId(p.getProductId());
			pd.setProductName(p.getProductName());
			pd.setProductDescription(p.getProductDescription());
			pd.setProductManufacturerName(p.getProductManufacturerName());
			pd.setProductPrice(p.getProductPrice());
			pd.setProductQuantity(p.getProductQuantity());
			pd.setCategoryId(Integer.toString(p.getCategory().getCategoryId()));
			pd.setRetailerId(Integer.toString(p.getRetailer().getRetailerId()));
			pd.setImgUrl(p.getImageUrl());
			
			productDtoList.add(pd);
		}
		
		return productDtoList;
	}
	
	@GetMapping(path = "/search-product")
	public List<ProductCategoryDto> searchProductByDescription(@RequestParam String description, HttpServletRequest request) {
		
		List<Product> productsList = productService.searchProductByDescription(description);
		
		List<ProductCategoryDto> productDtoList = new ArrayList();
		String projPath = request.getServletContext().getRealPath("/");
		String tempDownloadPath = projPath+"/downloads/";
		
		for(Product p:productsList) {
			File f = new File(tempDownloadPath);
			if(!f.exists()) {
				f.mkdir();
			}
			String targetFile = tempDownloadPath + p.getImageUrl();
			String uploadedImagePath = "d:/productimages/";
			String sourceFile = uploadedImagePath + p.getImageUrl();
			
			try {
				FileCopyUtils.copy(new File(sourceFile), new File(targetFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		for(Product p:productsList) {
			ProductCategoryDto pd = new ProductCategoryDto();
			pd.setProductId(p.getProductId());
			pd.setProductName(p.getProductName());
			pd.setProductDescription(p.getProductDescription());
			pd.setProductManufacturerName(p.getProductManufacturerName());
			pd.setProductPrice(p.getProductPrice());
			pd.setProductQuantity(p.getProductQuantity());
			pd.setCategoryId(Integer.toString(p.getCategory().getCategoryId()));
			pd.setRetailerId(Integer.toString(p.getRetailer().getRetailerId()));
			pd.setImgUrl(p.getImageUrl());
			
			productDtoList.add(pd);
		}
		
		return productDtoList;	
	}
	
	//@PostMapping("/upload-image")
	/*public Status uploadProductImage(ProductImageDto productImageDto) {
		String imageUploadLocation="d:/productimages/";			// create "productimages" folder in d drive
		String filename = productImageDto.getProductImage().getOriginalFilename();
		String targetFile = imageUploadLocation+filename;
		
		try {
			FileCopyUtils.copy(productImageDto.getProductImage().getInputStream(), new FileOutputStream(targetFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		} catch (IOException e) {
			e.printStackTrace();
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
		
		Product product = productService.findAProduct(productImageDto.getProductId());
		product.setImageUrl(filename);
		productService.addAProductWithCategory(product);
		
		Status status = new Status();
		status.setStatus(StatusType.SUCCESS);
		status.setMessage("Uploaded!");
		return status;
	}*/
	
	
	@GetMapping("/view-product")
	public ProductCategoryDto viewProduct(@RequestParam("productId") int productId, HttpServletRequest request) {
		Product product = productService.findAProduct(productId);
		String projPath = request.getServletContext().getRealPath("/");
		String tempDownloadPath = projPath+"/downloads/";
		//creating a folder within the project where we will place the product image getting fetched
		File f = new File(tempDownloadPath);
		if(!f.exists()) {
			f.mkdir();
		}
		String targetFile = tempDownloadPath + product.getImageUrl();
		String uploadedImagePath = "d:/productimages/";
		String sourceFile = uploadedImagePath + product.getImageUrl();
		
		try {
			FileCopyUtils.copy(new File(sourceFile), new File(targetFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ProductCategoryDto pd = new ProductCategoryDto();
		pd.setProductId(product.getProductId());
		pd.setProductName(product.getProductName());
		pd.setProductDescription(product.getProductDescription());
		pd.setProductManufacturerName(product.getProductManufacturerName());
		pd.setProductPrice(product.getProductPrice());
		pd.setProductQuantity(product.getProductQuantity());
		pd.setCategoryId(Integer.toString(product.getCategory().getCategoryId()));
		pd.setRetailerId(Integer.toString(product.getRetailer().getRetailerId()));
		pd.setImgUrl(product.getImageUrl());
		
		return pd;
	}
	
	@PutMapping("/update-product-retailer")
	public Status updateProduct(@RequestBody RetailerProductDto productDto) {
		
		Product product = new Product();
		product.setProductName(productDto.getProductName());
		product.setProductDescription(productDto.getProductDescription());
		product.setProductManufacturerName(productDto.getProductManufacturerName());
		product.setProductQuantity(productDto.getProductQuantity());
		product.setProductPrice(productDto.getProductPrice());
		product.setImageUrl(productDto.getImageUrl());
		product.setProductId(productDto.getProductId());
		Retailer ret = retailerService.findARetailer(productDto.getRetailerId());
		Category cat = categoryService.findACategory(productDto.getCategoryId());
		product.setRetailer(ret);
		product.setCategory(cat);
		
		int prodId = productService.updateProduct(product);
		Status status = new Status();
		status.setStatus(StatusType.SUCCESS);
		status.setMessage("Product with ProductID "+prodId+" Updated successfully!");
		return status;
	}
	
	@GetMapping("/view-retailer-product")
	public List<RetailerProductDto> viewProductByRetailer(@RequestParam("retailerId") int retailerId) {
		
		List<Product> products = productService.viewProductByRetailer(retailerId);
		List<RetailerProductDto> retailerProducts=new ArrayList<RetailerProductDto>();
		
		for(Product p:products) {
			RetailerProductDto dto=new RetailerProductDto();
			dto.setProductId(p.getProductId());
			dto.setProductName(p.getProductName());
			dto.setProductDescription(p.getProductDescription());
			dto.setProductPrice(p.getProductPrice());
			dto.setProductQuantity(p.getProductQuantity());
			dto.setProductManufacturerName(p.getProductManufacturerName());
			dto.setRetailerId(p.getRetailer().getRetailerId());
			dto.setImageUrl(p.getImageUrl());
			dto.setRetailerName(p.getRetailer().getRetailerName());
			dto.setCategoryName(p.getCategory().getCategoryName());
			dto.setCategoryId(p.getCategory().getCategoryId());
			System.out.println(p.getImageUrl());
			retailerProducts.add(dto);
		}
		for(RetailerProductDto a:retailerProducts) {
			System.out.println(a.getProductName());
		}
		return retailerProducts;
	}
	
}
