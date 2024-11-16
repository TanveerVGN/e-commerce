package com.e_commerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.e_commerce.module.Category;
import com.e_commerce.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/categoryimage";
	@PostMapping("/admin/saveCategory")
	public String addCategory(@ModelAttribute("category") @RequestBody Category category,@RequestParam("categoryImage")
	MultipartFile file, @RequestParam("imgName") String imgName,
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
		category.setImageName(imageUUID);
	Category addcategory = categoryService.addCategory(category);
	if (addcategory != null) {
		session.setAttribute("msg", "Register Successfully!....");
	} else {
		session.setAttribute("msg", "Somthing wrong...!!");
	}
		return "redirect:/admin/listofCategory";
	}
}

