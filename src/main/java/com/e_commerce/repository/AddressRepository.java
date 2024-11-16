package com.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.module.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findAllByuserId(Long id);
}
