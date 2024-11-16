package com.e_commerce.module;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String fullname;
	private String gender;
	private String mobile;
	private String email;
	private String password;
	private String role;
	
	/* ======================= */
//	private Boolean isEnable;
//	private Boolean accountNonLocked;
//	private Integer failedAttempt;
//	private Date lockTime;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> address = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> review = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)	
	@JsonIgnore
	private List<CustomerOrder> customerOrder = new ArrayList<>();
	
	private LocalDate createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public List<CustomerOrder> getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(List<CustomerOrder> customerOrder) {
		this.customerOrder = customerOrder;
	}

	public User(Long id, String fullname, String gender, String mobile, String email, String password, String role,
			List<Address> address, List<Review> review, List<CustomerOrder> customerOrder, LocalDate createdAt) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.gender = gender;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
		this.role = role;
		this.address = address;
		this.review = review;
		this.customerOrder = customerOrder;
		this.createdAt = createdAt;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
