package com.e_commerce.service;

import java.security.Principal;
import java.util.List;

import com.e_commerce.dto.CustomerOrderDTO;
import com.e_commerce.module.CustomerOrder;


public interface CustomerOrderService {
	// (1- Direct order)
		public CustomerOrder saveBuyNowCustomerOrder(CustomerOrderDTO customerOrderDTO, Principal principal);
		
		// (2- Cart items order)
		public List<CustomerOrder> saveCartItemsCustomerOrder(CustomerOrderDTO customerOrderDTO, Principal principal);

		public List<CustomerOrder> listOfCustomerOrder();

		public List<CustomerOrder> listOfCustomerOrderById(Long id);
		
		public CustomerOrder getParticularCustomerOrderById(Long id);
}
