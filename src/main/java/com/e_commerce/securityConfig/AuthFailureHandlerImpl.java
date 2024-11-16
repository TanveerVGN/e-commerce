//package com.e_commerce.securityConfig;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import com.e_commerce.module.User;
//import com.e_commerce.repository.UserRepository;
//import com.e_commerce.service.UserService;
//
//import UniversalPackage.AppConstant;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//@Component
//public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler{
//
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Override
//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException exception) throws IOException, ServletException {
//
//		String email = request.getParameter("username");
//
//		User user = userRepository.findByEmail(email);
//
//		if (user.getIsEnable()) {
//
//			if (user.getAccountNonLocked()) {
//
//				if (user.getFailedAttempt() < AppConstant.ATTEMPT_TIME) {
//					userService.increaseFailedAttempt(user);
//				} else {
//					userService.userAccountLock(user);
//					exception = new LockedException("Your account is locked !! failed attempt 3");
//				}
//			} else {
//
//				if (userService.unlockAccountTimeExpired(user)) {
//					exception = new LockedException("Your account is unlocked !! Please try to login");
//				} else {
//					exception = new LockedException("your account is Locked !! Please try after sometimes");
//				}
//			}
//
//		} else {
//			exception = new LockedException("your account is inactive");
//		}
//		
//		super.setDefaultFailureUrl("/login");
//		super.onAuthenticationFailure(request, response, exception);
//	}
//}
