package com.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.e_commerce.module.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{
	@Query("SELECT cat FROM Category cat WHERE cat.name LIKE %?1%")
	List<Category> serach(String keyword);
	
	public Boolean existsByName(String name);
}
