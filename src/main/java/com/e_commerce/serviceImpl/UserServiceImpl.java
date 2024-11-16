package com.e_commerce.serviceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.e_commerce.dto.UserDto;
import com.e_commerce.module.User;
import com.e_commerce.repository.UserRepository;
import com.e_commerce.service.UserService;

import jakarta.servlet.http.HttpSession;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public User createUser(UserDto userDto) {
		// TODO Auto-generated method stub 
		User user = new User();
		user.setFullname(userDto.getFullname());
		user.setGender(userDto.getGender());
		user.setMobile(userDto.getMobile());
		user.setEmail(userDto.getEmail());
//		user.setIsEnable(true);
//		user.setAccountNonLocked(true);
//		user.setFailedAttempt(0);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		if(userDto.getRole()==null)
		{
			user.setRole("ROLE_USER");
		}
		else
		{
			user.setRole(userDto.getRole());
			
		}
		
		user.setCreatedAt(LocalDate.now());
		return userRepository.save(user);


	}

	@Override
	public User checkEmail(String email) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public User getOrEditUserById(Long id) {
		// TODO Auto-generated method stub
		Optional<User> findById = userRepository.findById(id);
		User user = findById.get();
		return user;
	}

	@Override
	public boolean existEmail(String email) {
		// TODO Auto-generated method stub
		boolean existsByEmail = userRepository.existsByEmail(email);
		return existsByEmail;
	}

	@Override
	public List<User> listOfUsers() {
		// TODO Auto-generated method stub
		List<User> findAll = userRepository.findAll();
		return findAll;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

	public void revSessMsgWrg()
	{
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg_wrg");
	}
	
	
	public void revSessMsgRig()
	{
		HttpSession session= ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg_rig");
	}

	@Override
	public Long countTheUser() {
		// TODO Auto-generated method stub
		long countUser = userRepository.count();
		return countUser;
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}
	
	

//	@Override
//	public void increaseFailedAttempt(User user) {
//		// TODO Auto-generated method stub
//		int attempt = user.getFailedAttempt() + 1;
//		user.setFailedAttempt(attempt);
//		userRepository.save(user);
//	}
//
//	@Override
//	public void userAccountLock(User user) {
//		// TODO Auto-generated method stub
//		user.setAccountNonLocked(false);
//		user.setLockTime(new Date());
//		userRepository.save(user);
//	}
//
//	@Override
//	public boolean unlockAccountTimeExpired(User user) {
//		// TODO Auto-generated method stub
//		long lockTime = user.getLockTime().getTime();
//		long unLockTime = lockTime + AppConstant.UNLOCK_DURATION_TIME;
//
//		long currentTime = System.currentTimeMillis();
//
//		if (unLockTime < currentTime) {
//			user.setAccountNonLocked(true);
//			user.setFailedAttempt(0);
//			user.setLockTime(null);
//			userRepository.save(user);
//			return true;
//		}
//
//		return false;
//	}
	}

