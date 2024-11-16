package com.e_commerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.e_commerce.module.Product;
import com.e_commerce.module.Review;
import com.e_commerce.module.User;
import com.e_commerce.repository.UserRepository;
import com.e_commerce.service.CategoryService;
import com.e_commerce.service.ProductService;
import com.e_commerce.service.ReviewService;

import UniversalPackage.UniversalData;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;

	@ModelAttribute
	public void getLoggedUser(Model model, Principal principal) {
		if (principal != null) {
			String email = principal.getName();
			User user = userRepository.findByEmail(email);

			String role = user.getRole().toUpperCase();
			if (role.equalsIgnoreCase("ROLE_ADMIN")) {
				model.addAttribute("admin", role);
				model.addAttribute("user", user);
			} else {
				model.addAttribute("user", user);
			}
		}
	}

	@GetMapping("/home/for-all-type-product-filter")
	public String ForAllTypeProductFilter(Model model, @Param("keyword") String keyword) {
		// List of all categories!
		model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());

		// List of all products!
		model.addAttribute("listofProduct", productService.listofProduct(keyword));

		// Cart view!
		model.addAttribute("cartCountValue", UniversalData.cart.size()); // Attention Here !!
		return "home-for-all-type-product-filter";
	}

	@GetMapping({ "/", "/home" })
	public String home(Model model, @Param("keyword") String keyword) {

		model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());

		model.addAttribute("productList", productService.listofProduct(keyword));
		model.addAttribute("listofAllProduct", productService.listofProduct(keyword));

		List<Product> listofProduct = productService.listofProduct(keyword);

//		List<Product> Electronics = listofProduct.stream()
//				.filter(b -> b.getCategory().getName().equalsIgnoreCase("Electronics"))
//				.collect(Collectors.toList());
//		model.addAttribute("listofElectronics", Electronics);

		List<Product> Electronics = listofProduct.stream()
				.filter(b -> b.getCategory().getName().equalsIgnoreCase("Electronics")).limit(1)
				.collect(Collectors.toList());
		model.addAttribute("listofElectronics", Electronics);

		// Fashion!
		List<Product> Fashion = listofProduct.stream()
				.filter(f -> f.getCategory().getName().equalsIgnoreCase("Fashion")).limit(1)
				.collect(Collectors.toList());
		model.addAttribute("listofFashion", Fashion);

//		List<Product> Fashion = listofProduct.stream()
//				.filter(f -> f.getCategory().getName().equalsIgnoreCase("Fashion")).collect(Collectors.toList());
//		model.addAttribute("listofFashion", Fashion);

//		List<Product> Cosmetic = listofProduct.stream().filter(f -> f.getCategory().getName().equalsIgnoreCase("Cosmetic")).collect(Collectors.toList());
//		model.addAttribute("listofCosmetic", Cosmetic);

		List<Product> Cosmetic = listofProduct.stream()
				.filter(f -> f.getCategory().getName().equalsIgnoreCase("Cosmetic")).limit(1)
				.collect(Collectors.toList());
		model.addAttribute("listofCosmetic", Cosmetic);

		// Shoes!
		List<Product> Shoes = listofProduct.stream().filter(s -> s.getCategory().getName().equalsIgnoreCase("shoes"))
				.limit(1).collect(Collectors.toList());
		model.addAttribute("listofShoes", Shoes);

		// Bags!
		List<Product> Laptopbags = listofProduct.stream()
				.filter(lb -> lb.getCategory().getName().equalsIgnoreCase("Bags")).limit(1)
				.collect(Collectors.toList());
		model.addAttribute("listofLaptopbags", Laptopbags);

		// Books!
		List<Product> Books = listofProduct.stream().filter(bk -> bk.getCategory().getName().equalsIgnoreCase("Books"))
				.limit(1).collect(Collectors.toList());
		model.addAttribute("listofBookss", Books);

		// Mobile
		List<Product> Mobile = listofProduct.stream().filter(b -> b.getCategory().getName().equalsIgnoreCase("Mobile"))
				.limit(1).collect(Collectors.toList());
		model.addAttribute("listofMobile", Mobile);

		// Watches
		List<Product> Watches = listofProduct.stream()
				.filter(b -> b.getCategory().getName().equalsIgnoreCase("Watches")).limit(1)
				.collect(Collectors.toList());
		model.addAttribute("listofWatches", Watches);
		
		//Toys
		List<Product> Toys = listofProduct.stream().filter(b -> b.getCategory().getName().equalsIgnoreCase("Toys")).limit(1).collect(Collectors.toList());
		model.addAttribute("listofToys", Toys);

		// Cart view!
		model.addAttribute("cartCountValue", UniversalData.cart.size());

		return "home";
	}

	@GetMapping("/home/getProductByCategorySearch/{id}")
	public String getProductByCategorySearch(@PathVariable(value = "id") Long id, Model model) {

		model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
		model.addAttribute("cartCountValue", UniversalData.cart.size());

		model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
		List<Product> listofProductByCategorySearch = productService.listofProductByCategorySearch(id); // Attention
																										// Here !!
		model.addAttribute("listofProductByCategorySearch", listofProductByCategorySearch);
		model.addAttribute("cartCountValue", UniversalData.cart.size());
		// limit to one for slide banner!
		List<Product> collect = listofProductByCategorySearch.stream().collect(Collectors.toList());
		model.addAttribute("categoryFound", collect);

		// Attention Here !!
		return "shop";
	}

	@GetMapping("/home/getDetailOfParticularProduct/{id}")
	public String getDetailOfParticularProduct(@PathVariable(value = "id") Long id, Model model) {
		Product getorEditProduct = productService.getOrEditProductById(id);
		model.addAttribute("getorEditProduct", getorEditProduct);

		String stockGet = getorEditProduct.getStock();
		if (stockGet.equalsIgnoreCase("0")) {
			String out_of_stock = "Out of stock";
			model.addAttribute("OutOfStock", out_of_stock);
		} else {
			String in_stock = "In stock";
			model.addAttribute("InStock", in_stock);
		}

		// This is send list of categories on view at the NAVBAR section!

		model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());

		// Cart value Attention Here !!
		model.addAttribute("cartCountValue", UniversalData.cart.size());

		List<Review> allreviewById = reviewService.findAllReviewByproductId(id);
		model.addAttribute("allreviewById", allreviewById);

		// Getting size for list of review by particular product id !!
		int totalReview = allreviewById.size();
		model.addAttribute("totalReview", totalReview);

		return "detail";
	}

	// FILTER PRICE SECTION...!!

	@GetMapping("/home/FilterPriceUnder500")
	public String searchProductUnder500(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(product -> product.getDiscountedPrice() <= 500)
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterPrice500To1000")
	public String searchProduct500To1000(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream()
				.filter(product -> product.getDiscountedPrice() >= 500 && product.getDiscountedPrice() <= 1000)
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterPrice1000To5000")
	public String searchProduct1000To5000(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream()
				.filter(product -> product.getDiscountedPrice() >= 1000 && product.getDiscountedPrice() <= 5000)
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterPrice5000To10000")
	public String searchProduct5000To10000(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream()
				.filter(product -> product.getDiscountedPrice() >= 5000 && product.getDiscountedPrice() <= 10000)
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterPrice10000To20000")
	public String searchProduct10000To20000(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream()
				.filter(product -> product.getDiscountedPrice() >= 10000 && product.getDiscountedPrice() <= 20000)
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterPrice20000To50000")
	public String searchProduct20000To50000(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream()
				.filter(product -> product.getDiscountedPrice() >= 20000 && product.getDiscountedPrice() <= 50000)
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterPrice50000To100000")
	public String searchProduct50000To100000(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream()
				.filter(product -> product.getDiscountedPrice() >= 50000 && product.getDiscountedPrice() <= 100000)
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterPriceOver100000")
	public String searchProductOver100000(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(product -> product.getDiscountedPrice() >= 100000)
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	// FILTER BRAND SECTION...!!

	@GetMapping("/home/FilterByBajaj")
	public String serchProductByBrandBJ(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(b -> b.getBrand().equalsIgnoreCase("Bajaj"))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterBySafari")
	public String serchProductByBrandSf(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(b -> b.getBrand().equalsIgnoreCase("Safari"))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterByApple")
	public String serchProductByBrandAp(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(b -> b.getBrand().equalsIgnoreCase("Apple"))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterByCampus")
	public String serchProductByBrandCp(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(b -> b.getBrand().equalsIgnoreCase("Campus"))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterByNike")
	public String serchProductByBrandT(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(b -> b.getBrand().equalsIgnoreCase("Nike"))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterByLakme")
	public String serchProductByBrandLakme(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(b -> b.getBrand().equalsIgnoreCase("Lakme"))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	@GetMapping("/home/FilterByCurren")
	public String serchProductByBrandCurren(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(b -> b.getBrand().equalsIgnoreCase("Curren"))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}

	// Filter By Color!...
	@GetMapping("/home/FilterByWhite")
	public String serchProductByBrandW(String keyword, Model model) {
		List<Product> listofProduct = productService.listofProduct(keyword);
		List<Product> collect = listofProduct.stream().filter(b -> b.getBrand().equalsIgnoreCase("Brown"))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			model.addAttribute("listofProduct", collect);
			model.addAttribute("listofCategoryWithOutKey", categoryService.listofCategory());
			return "home-for-all-type-product-filter";
		} else {
			return "nothing";
		}
	}
}
