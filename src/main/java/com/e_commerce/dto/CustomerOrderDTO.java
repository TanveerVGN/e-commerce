package com.e_commerce.dto;

import java.time.LocalDate;

public class CustomerOrderDTO {
private Long id;
	
	private Long userId;
	
	private Long addressId;
	
	private Long productId;
	
	private LocalDate createdAt;
	
	private Long customerOrderID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCustomerOrderID() {
		return customerOrderID;
	}

	public void setCustomerOrderID(Long customerOrderID) {
		this.customerOrderID = customerOrderID;
	}

	public CustomerOrderDTO(Long id, Long userId, Long addressId, Long productId, LocalDate createdAt,
			Long customerOrderID) {
		super();
		this.id = id;
		this.userId = userId;
		this.addressId = addressId;
		this.productId = productId;
		this.createdAt = createdAt;
		this.customerOrderID = customerOrderID;
	}

	public CustomerOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
