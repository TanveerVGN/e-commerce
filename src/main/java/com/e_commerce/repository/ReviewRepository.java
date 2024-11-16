package com.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.module.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findAllByproductId(Long id);

}
