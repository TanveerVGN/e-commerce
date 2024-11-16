package com.e_commerce.service;

import java.security.Principal;
import java.util.List;

import com.e_commerce.dto.ReviewDto;
import com.e_commerce.module.Review;


public interface ReviewService {
public Review saveReview(ReviewDto reviewDto, Principal principal);
	
	public List<Review> listOfReview();
	
	public String deleteReview(Long id);
	
	public Review getOrEditReviewById(Long id);
	
	public List<Review> findAllReviewByproductId(Long id);
}
