package com.e_commerce.module;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ForgotPasswordToken {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String token;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "admin_id")
	private User user;
	
	@Column(nullable = false)
	private LocalDateTime expireTime;
	
	@Column(nullable = false)
	private boolean isUsed;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public ForgotPasswordToken(long id, String token, User user, LocalDateTime expireTime, boolean isUsed) {
		super();
		this.id = id;
		this.token = token;
		this.user = user;
		this.expireTime = expireTime;
		this.isUsed = isUsed;
	}

	public ForgotPasswordToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}