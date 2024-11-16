package com.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.module.CustomerOrder;


public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>{
	List<CustomerOrder> findAllByuserId(Long id);
}
