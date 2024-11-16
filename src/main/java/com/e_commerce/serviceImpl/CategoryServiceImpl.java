package com.e_commerce.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.e_commerce.module.Category;
import com.e_commerce.repository.CategoryRepository;
import com.e_commerce.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		Category save = categoryRepository.save(category);
		return save;
	}

	

	@Override
	public void deleteCategory(Long id) {
		// TODO Auto-generated method stub
		categoryRepository.deleteById(id);
	}

	@Override
	public Category getOrEditCategoryById(Long id) {
		// TODO Auto-generated method stub
		Optional<Category> findById = categoryRepository.findById(id);
		Category category = findById.get();
		return category;
	}

	@Override
	public List<Category> listofCategory() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Long countTheCategory() {
		// TODO Auto-generated method stub
		long countCategory = categoryRepository.count();
		return countCategory;
	}



	@Override
	public List<Category> listofCategory(String keyword) {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}



	@Override
	public Page<Category> findPaginated(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable= PageRequest.of(pageNo - 1, pageSize);
		Page<Category> findAllPage = categoryRepository.findAll(pageable);
		return findAllPage;
	}


	/* ============================ */
			

	@Override
	public Boolean existCategory(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.existsByName(name);
	}

}
