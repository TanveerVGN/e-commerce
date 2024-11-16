package com.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.module.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, Long>{
	ForgotPasswordToken findByToken(String token);

}
