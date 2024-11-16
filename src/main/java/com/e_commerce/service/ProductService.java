package com.e_commerce.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.e_commerce.dto.ProductDto;
import com.e_commerce.module.Product;



public interface ProductService {
public Product addProduct(ProductDto productDTO);
	
	public List<Product> listofProduct(String keyword);
	
	public Page<Product> findPaginated(int pageNo, int pageSize);
	
	public void deleteProduct(Long id);
	
	public Product getOrEditProductById(Long id);
	
	public List<Product> listofProductByCategorySearch(Long id);
	
	public Long countTheProduct();
//	public List<Product> listProduct();
}
