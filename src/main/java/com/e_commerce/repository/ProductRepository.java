package com.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.e_commerce.module.Product;


public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("SELECT pro FROM Product pro WHERE pro.name LIKE %?1%"
			+ "OR pro.name LIKE %?1%"
			+ "OR pro.color LIKE %?1%"
			+ "OR pro.brand LIKE %?1%")
	List<Product> search(String keyword);

	// See Attention Here !!
	List<Product> findAllBycategoryId(Long id);
	
}
