package com.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.module.User;


public interface UserRepository extends JpaRepository<User, Long>{
public User findByEmail(String email);
	
	public boolean existsByEmail(String email);
	
//User findByUsername(String username);
}
