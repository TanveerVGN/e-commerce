package com.e_commerce.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.e_commerce.dto.ProductDto;
import com.e_commerce.module.Category;
import com.e_commerce.module.Product;
import com.e_commerce.module.User;
import com.e_commerce.repository.UserRepository;
import com.e_commerce.service.CategoryService;
import com.e_commerce.service.ProductService;
import com.e_commerce.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;

	@GetMapping("/admin/adminHome")
	public String adminHome(Model model) {
//		// Total Products Count!
		Long countTheProduct = productService.countTheProduct();
		model.addAttribute("countTheProduct", countTheProduct);
//		
//		// Total Categories Count!
		Long countTheCategory = categoryService.countTheCategory();
		model.addAttribute("countTheCategory", countTheCategory);
		
		//Total User Count!..
		Long countTheUser = userService.countTheUser();
		model.addAttribute("countTheUser", countTheUser);
		return "admin-dashboard";
	}

	@ModelAttribute
	public void getLoggedUser(Model model, Principal principal) {
		if (principal != null) {
			String email = principal.getName();
			User user = userRepository.findByEmail(email);
			model.addAttribute("user", user);
		}
	}

	
	@GetMapping("/admin/categoryList")
	public String getAdminCategory(Model model)
	{
			return "category-list";
		
			}
	
	/* Category!........... */
	
	@GetMapping("/admin/getCategory")
	public String getCategoryPage(Model model)
	{
		Category category = new Category();
		model.addAttribute("category", category);
		return "add-category";
	}
	
//	public static String uploadDir1 = System.getProperty("user.dir") +"/src/main/resources/static/categoryImage";
//	@PostMapping("/admin/saveCategory")
//	public String addCategory(@ModelAttribute("category") @RequestBody Category category,@RequestParam("categoryImage") MultipartFile file, @RequestParam("imgName") String imgName,
//			HttpSession session) throws IOException {
//		String imageUUID;
//		if(!file.isEmpty())
//		{
//			imageUUID = file.getOriginalFilename();
//			Path fileNameAndPath = Paths.get(uploadDir1,imageUUID);
//			Files.write(fileNameAndPath, file.getBytes());
//		}
//		else
//		{
//			imageUUID = imgName;
//		}
//		category.setCategoryImage(imageUUID);
//		categoryService.addCategory(category);
//		return "redirect:/admin/listofCategory";
//	}
	
	@GetMapping("/admin/getOrEditCategory/{id}")
	public String getOrEditCategoryById(@PathVariable(value = "id") Long id, Model model)
	{
		Category getorEditCategoryById = categoryService.getOrEditCategoryById(id);
		//Category category = new Category();
		
		model.addAttribute("category", getorEditCategoryById);
		return "edit-category";
	}
	
	
	@PostMapping("/admin/updateCategory")
	public String updateCategory(@ModelAttribute(value = "category") Category category, long id)
			 {
		categoryService.addCategory(category);
		
		return"redirect:/admin/listofCategory";
	}
	
	// This is search features is not applicable on Pagination .
		@GetMapping("/admin/listofCategory")
		public String listofCategory(Model model, @Param("keyword") String keyword)
		{
			if(keyword!= null)
			{
				List<Category> listofCategory = categoryService.listofCategory(keyword);
				model.addAttribute("listofCategory", listofCategory);
				return "category-list";
			}
			else
			{
				return findCategroyPaginated(1, model);
			}
			
		}

		/* ===========Pagination========= */
		@GetMapping("/admin/Catpage/{pageNum}")
		public String findCategroyPaginated(@PathVariable(value = "pageNum") int pageNo, Model model)
		{
			int pageSize= 5;
			Page<Category> page = categoryService.findPaginated(pageNo, pageSize);
			List<Category> listofCategory = page.getContent();
			
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listofCategory", listofCategory);
			return "category-list";
		}
		
		@GetMapping("/admin/deleteCategory/{id}")
		public String deleteCategory(@PathVariable(value = "id") Long id)
		{
			categoryService.deleteCategory(id);
			return"redirect:/admin/listofCategory";
		}
		
		
		
	
	
	/* ===========================End Category============================================= */
	
	

	/* ===================PRODUCT SECTION================================ */

	@GetMapping("/admin/getProductPage")
	public String getProductPage(Model model) {
		ProductDto productDto = new ProductDto();
		model.addAttribute("productDto", productDto);
		model.addAttribute("listofCategoryWithOutKeyw", categoryService.listofCategory());
		return "add-product";
	}

	public static String uploadDir = System.getProperty("user.dir") +"/src/main/resources/static/productimages";
	@PostMapping("/admin/addProduct")
	public String addProduct(@ModelAttribute("productDTO")@RequestBody ProductDto productDTO,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName,
			HttpSession session) throws IOException {
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
		
		productDTO.setImageName(imageUUID);
		Product addProductWithCondition = productService.addProduct(productDTO);
		if (addProductWithCondition != null) {
			session.setAttribute("msg", "Register Successfully!....");
		} else {
			session.setAttribute("msg", "Somthing wrong...!!");
		}
		return "redirect:/admin/listofProduct";
	}
	
	
	@GetMapping("/admin/listofProduct")
	public String listofProduct(Model model, @Param("keyword") String keyword)
	{
		if(keyword!=null)
		{
			List<Product> listofProduct = productService.listofProduct(keyword);
			model.addAttribute("listofProduct", listofProduct);
			return "product-list";
		}
		else
		{
			return findProductPaginated(1, model);
		}
	}
	
	/* ===========Pagination========= */
	@GetMapping("/admin/page/{pageNo}")
	public String findProductPaginated(@PathVariable(value = "pageNo") int pageNo, Model model)
	{
		int pageSize= 8;
		Page<Product> page = productService.findPaginated(pageNo, pageSize);
		List<Product> listofProduct = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listofProduct", listofProduct);
		return "list-products";
	}
	
	@GetMapping("/admin/deleteProduct/{id}")
	public String deleteProduct(@PathVariable(value = "id") Long id)
	{
		productService.deleteProduct(id);
		return "redirect:/admin/listofProduct";
	}

//	@GetMapping("/admin/listofProduct")
//	public String listofProduct(Model model)
//	{
//		List<Product> productlist = productService.listProduct();
//		model.addAttribute("productlist", productlist);
//			return "list-products";
//	
//}
}
