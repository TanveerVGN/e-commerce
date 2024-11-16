package com.e_commerce.service;

import java.util.List;

import com.e_commerce.dto.UserDto;
import com.e_commerce.module.User;



public interface UserService {
	User createUser(UserDto userDto);

	User checkEmail(String email);

	User getOrEditUserById(Long id);

	boolean existEmail(String email);

	List<User> listOfUsers();

	void deleteUser(Long id);
	
	User findByEmail(String email);
	
	public Long countTheUser();
	
	public User save(User user);
//	public void increaseFailedAttempt(User user);
//	public void userAccountLock(User user);
//	public boolean unlockAccountTimeExpired(User user);
}
