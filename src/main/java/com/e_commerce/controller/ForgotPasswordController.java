//package com.e_commerce.controller;
//
//import java.io.UnsupportedEncodingException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.e_commerce.module.ForgotPasswordToken;
//import com.e_commerce.module.User;
//import com.e_commerce.repository.ForgotPasswordRepository;
//import com.e_commerce.service.ForgotTokenService;
//import com.e_commerce.service.UserService;
//
//import jakarta.mail.MessagingException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//
//@Controller
//public class ForgotPasswordController {
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private ForgotTokenService forgotTokenService;
//	
//	@Autowired
//	private ForgotPasswordRepository forgotPasswordRepository;
//	
//	@Autowired
//	PasswordEncoder passwordEncoder;
//	
//	@GetMapping("/password-request")
//	public String passwordRequest()
//	{
//		return "password-request";
//	}
//	
//	@PostMapping("/password-request")
//	public String savePasswordRequest(@RequestParam("username") String email, Model model)
//	{
//		User user = userService.findByEmail(email);
//		if(user==null) 
//		{
//			model.addAttribute("error", "This Email is not registerd");
//			return "password-request";
//		}
//		ForgotPasswordToken forgotPasswordToken= new ForgotPasswordToken();
//		forgotPasswordToken.setExpireTime(forgotTokenService.expireTimeRange());
//		forgotPasswordToken.setToken(forgotTokenService.generateToken());
//		forgotPasswordToken.setUser(user);
//		forgotPasswordToken.setUsed(false);
//		
//		forgotPasswordRepository.save(forgotPasswordToken);
//		
//		String emailLink= "http://localhost:8081/reset-password?token="+forgotPasswordToken.getToken();
//		
//		try
//		{
//			forgotTokenService.sendEmail(user.getEmail(), "Password Reset Link", emailLink);
//		} 
//		catch (UnsupportedEncodingException | MessagingException e)
//		{
//			e.printStackTrace();
//			model.addAttribute("error", "Error while sending email...");
//			return "password-request";
//		}
//		return "redirect:/password-request?success";
//	}
//	
//	@GetMapping("/reset-password")
//	public String resetPassword(@Param(value="token") String token, Model model, HttpSession session)
//	{
//		session.setAttribute("token", token);
//		ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findByToken(token);
//		return forgotTokenService.checkValidity(forgotPasswordToken, model);
//	}
//	
//	@PostMapping("/reset-password")
//	public String saveResetPassword(HttpServletRequest httpServletRequest, HttpSession session, Model model)
//	{
//		 String password= httpServletRequest.getParameter("password");
//		 String token= (String) session.getAttribute("token");
//		 
//		 ForgotPasswordToken forgotPasswordToken= forgotPasswordRepository.findByToken(token);
//		 User user= forgotPasswordToken.getUser();
//		 user.setPassword(passwordEncoder.encode(password));
//		 forgotPasswordToken.setUsed(true);
//		 userService.save(user);
//		 forgotPasswordRepository.save(forgotPasswordToken);
//		 model.addAttribute("message", "You have successfully reset your password!");
//		 return "reset-password";
//	}
//}
