package com.e_commerce.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.e_commerce.module.CustomerOrder;
import com.e_commerce.module.ForgotPasswordToken;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ForgotTokenService {
	
	@Autowired
	private JavaMailSender mailSender;

	public Boolean sendMail(String url, String reciepentEmail) throws UnsupportedEncodingException, MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("ansaritanveer994@gmail.com", "Online Shopping");
		helper.setTo(reciepentEmail);

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + url
				+ "\">Change my password</a></p>";
		helper.setSubject("Password Reset");
		helper.setText(content, true);
		mailSender.send(message);
		return true;
	}

	public static String generateUrl(HttpServletRequest request) {

		// http://localhost:8080/forgot-password
		String siteUrl = request.getRequestURL().toString();

		return siteUrl.replace(request.getServletPath(), "");
	}
	
	String msg=null;;
	
	public Boolean sendMailForProductOrder(CustomerOrder order,String status) throws Exception
	{
		
		msg="<p>Hello [[name]],</p>"
				+ "<p>Thank you order <b>[[orderStatus]]</b>.</p>"
				+ "<p><b>Product Details:</b></p>"
				+ "<p>Name : [[productName]]</p>"
				+ "<p>Category : [[category]]</p>"
				+ "<p>Quantity : [[quantity]]</p>"
				+ "<p>Price : [[price]]</p>"
				+ "<p>Payment Type : [[paymentType]]</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("ansaritanveer994@gmail.com", "Online Shopping");
		helper.setTo(order.getUser().getEmail());

		msg=msg.replace("[[name]]",order.getAddress().getFirstName());
		msg=msg.replace("[[orderStatus]]",status);
		msg=msg.replace("[[productName]]", order.getProduct().getName());
		//msg=msg.replace("[[category]]", order.getProduct().getCategory());
		msg=msg.replace("[[quantity]]", order.getCreatedAt().toString());
		msg=msg.replace("[[price]]", order.getProduct().toString());
		//msg=msg.replace("[[paymentType]]", order.getPaymentType());
		
		helper.setSubject("Product Order Status");
		helper.setText(msg, true);
		mailSender.send(message);
		return true;
	}
	
	
	
	
//	@Autowired
//private	JavaMailSender javaMailSender;					
//	
//	
//	// Class level variable declaration !!
//	private final int MINUTES= 10;
//	public String generateToken()					//Step-2
//	{
//		return UUID.randomUUID().toString();		
//	}
//	
//	public LocalDateTime expireTimeRange()			//Step-3
//	{
//		return LocalDateTime.now().plusMinutes(MINUTES);
//	}
//	
//	public void sendEmail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException			//Step-4
//	{
//		MimeMessage mimeMessage= javaMailSender.createMimeMessage();
//		MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage);
//		
//		String emailContent= "<p>Hello!</p>"
//							 + "Click the link below to reset password:"
//							 + "<p><a href=\"" +emailLink+ "\">Link : Change my password</a></p>"
//							 + "<br>"
//							 + "Ignore this email if you did not made the request!";
//		
//		mimeMessageHelper.setText(emailContent, true);
//		mimeMessageHelper.setFrom("ansaritanveer994@gmail.com", "Coding Techniques Support");
//		mimeMessageHelper.setSubject(subject);	
//		mimeMessageHelper.setTo(to);
//		
//		javaMailSender.send(mimeMessage); 
//	}
//	
//	public boolean isExpired(ForgotPasswordToken forgotPasswordToken)
//	{
//		return LocalDateTime.now().isAfter(forgotPasswordToken.getExpireTime());
//	}
//	
//	public String checkValidity(ForgotPasswordToken forgotPasswordToken, Model model)
//	{
//		if(forgotPasswordToken == null)
//		{
//			model.addAttribute("error", "Invalid Token!");
//			return "error-page";
//		}
//		else if(forgotPasswordToken.isUsed())
//		{
//			model.addAttribute("error", "The token is already used!");
//			return "error-page";
//		}
//		else if(isExpired(forgotPasswordToken))
//		{
//			model.addAttribute("error", "The token is expired!");
//			return "error-page";
//		}
//		else
//		{
//			return"reset-password";
//		}
//	}
}
