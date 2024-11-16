package com.e_commerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e_commerce.dto.ReviewDto;
import com.e_commerce.module.Product;
import com.e_commerce.module.Review;
import com.e_commerce.module.User;
import com.e_commerce.repository.UserRepository;
import com.e_commerce.service.CategoryService;
import com.e_commerce.service.ProductService;
import com.e_commerce.service.ReviewService;

import UniversalPackage.UniversalData;



@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/review/getReviewPage/{id}")
	public String getReviewPageById(@PathVariable(value = "id") Long id, Model model, Principal principal)
	{
		ReviewDto reviewDto = new ReviewDto();
		model.addAttribute("reviewDto", reviewDto);
		
		Product getorEditProduct = productService.getOrEditProductById(id);  		// This is return  all  product details !!
		Long productIdFound = getorEditProduct.getId();							 	// And here we get product id only...we can get all details !!
		model.addAttribute("productIdFound", productIdFound);				  		// And here we send above product id only on front-end...we can send all details on front-end!!
			
		model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
		model.addAttribute("cartCountValue", UniversalData.cart.size());   	  		//Attention Here !!
		
		// Simply list of all review !!
		List<Review> listOfReview = reviewService.listOfReview();
		model.addAttribute("listOfReview", listOfReview);
		
		// Attention Here !!
		// Getting all list of review by particular product id!!
		// Own custom method !!
		// This model is send on HomeController at details page ...please see!...yahaa par iska humane koi use nahi kiya hai, just for checking concept.
		List<Review> reviewById = reviewService.findAllReviewByproductId(id);
		model.addAttribute("reviewById", reviewById);

		if (principal != null)
		{
			String email = principal.getName();
			User user = userRepository.findByEmail(email);
			model.addAttribute("user", user);
		}
			
		return "rate-review";
	}
	
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/Reviewimages";
	@PostMapping("/review/addReview")
	public String addReview(@ModelAttribute("reviewDto") @RequestBody ReviewDto reviewDto, Principal principal,@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName, RedirectAttributes redirectAttributes) throws IOException
	{
		String imageUUID;
		if(!file.isEmpty())
		{
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}
		else
		{
			imageUUID = imgName;
		}
		reviewDto.setImageName(imageUUID);
		
Review addreview= reviewService.saveReview(reviewDto, principal);
if(addreview != null)
{
	redirectAttributes.addFlashAttribute("message", "Review added");
}else {
	redirectAttributes.addFlashAttribute("message", "Something Error");
}
		
//		return "redirect:/";
		
		return "redirect:/review/getReviewPage/" + reviewDto.getProductId();
	}
	
	
	
//	@PostMapping("/review/addReview")
//	public String addReview(@ModelAttribute("reviewDto") ReviewDto reviewDto, Principal principal, RedirectAttributes redirectAttributes)
//	{
//		reviewService.saveReview(reviewDto, principal);
//		redirectAttributes.addFlashAttribute("message", "Review added");
//		
//		
//		return "redirect:/review/getReviewPage/" + reviewDto.getProductId();
//	}
	
	
	
	
//	@GetMapping("/review/getReviewPage/{id}")
//	public String getReviewPageById(@PathVariable(value = "id") Long id, Model model, Principal principal)
//	{
//		ReviewDto reviewDto = new ReviewDto();
//		model.addAttribute("reviewDto", reviewDto);
//		
//		Product getorEditProduct = productService.getOrEditProductById(id);  		// This is return  all  product details !!
//		Long productIdFound = getorEditProduct.getId();							 	// And here we get product id only...we can get all details !!
//		model.addAttribute("productIdFound", productIdFound);				  		// And here we send above product id only on front-end...we can send all details on front-end!!
//			
//		model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
//		model.addAttribute("cartCountValue", UniversalData.cart.size());   	  		//Attention Here !!
//		
//		// Simply list of all review !!
//		List<Review> listOfReview = reviewService.listOfReview();
//		model.addAttribute("listOfReview", listOfReview);
//		
//		// Attention Here !!
//		// Getting all list of review by particular product id!!
//		// Own custom method !!
//		// This model is send on HomeController at details page ...please see!...yahaa par iska humane koi use nahi kiya hai, just for checking concept.
//		List<Review> reviewById = reviewService.findAllReviewByproductId(id);
//		model.addAttribute("reviewById", reviewById);
//
//		if (principal != null)
//		{
//			String email = principal.getName();
//			User user = userRepository.findByEmail(email);
//			model.addAttribute("user", user);
//		}
//			
//		return "rate-review";
//	}
//	
//	
//	@PostMapping("/review/addReview")
//	public String addReview(@ModelAttribute("reviewDto") ReviewDto reviewDto, Principal principal, RedirectAttributes redirectAttributes)
//	{
//		reviewService.saveReview(reviewDto, principal);
//		redirectAttributes.addFlashAttribute("message", "Review added");
//		
////		return "redirect:/";
//		
//		return "redirect:/review/getReviewPage/" + reviewDto.getProductId();
//	}
}
