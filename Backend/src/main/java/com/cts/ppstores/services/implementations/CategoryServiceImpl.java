package com.cts.ppstores.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.cts.ppstores.controllers.mappers.EntityDtoMapper;
import com.cts.ppstores.dtos.CategoryDto;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.exceptions.DuplicateEntryException;
import com.cts.ppstores.exceptions.NotFoundException;
import com.cts.ppstores.models.Category;
import com.cts.ppstores.repositories.CategoryRepo;
import com.cts.ppstores.services.interfaces.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response createCategory(CategoryDto categoryRequest) {
    	
    	log.info("Creating category: {}", categoryRequest);
    	
    	Category checkCategory = categoryRepo.findByName(categoryRequest.getName());
    	
    	if(checkCategory!=null) {
    		log.warn("Duplicate entry for category name: {}", categoryRequest.getName());
    		throw new DuplicateEntryException("Duplicate entry for category name: " + categoryRequest.getName());    		
    	}
    	
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        
        log.info("Category has been created successfully");
        
        return Response.builder()
                .status(200)
                .message("Category has been created successfully")
                .build();
    }

    @Override
    public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
    	
    	log.info("Updating category with ID {}: {}", categoryId, categoryRequest);

        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        
    	Category checkCategory = categoryRepo.findByName(categoryRequest.getName());
    	
    	if(checkCategory!=null && categoryId != checkCategory.getId()) {
    		log.warn("Duplicate entry for category name: {}", categoryRequest.getName());
    		throw new DuplicateEntryException("Duplicate entry for category name: " + categoryRequest.getName());    		
    	}
    	
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        
        log.info("Category has been updated successfully");
        
        return Response.builder()
                .status(200)
                .message("category has been updated successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
    	
    	log.info("Fetching all categories");
    	
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : categories) {
            CategoryDto categoryDto = entityDtoMapper.mapCategoryToDtoBasic(category);
            categoryDtoList.add(categoryDto);
        }
        
        log.info("Fetched all categories: {}", categoryDtoList);

        return  Response.builder()
                .status(200)
                .categoryList(categoryDtoList)
                .build();
    }

    @Override
    public Response getCategoryById(Long categoryId) {
    	
    	log.info("Fetching category with ID {}", categoryId);
    	
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToDtoBasic(category);
        
        log.info("Fetched category: {}", categoryDto);
        
        return Response.builder()
                .status(200)
                .category(categoryDto)
                .build();
    }

    @Override
    public Response deleteCategory(Long categoryId) {
    	
    	log.info("Deleting category with ID {}", categoryId);
    	
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        categoryRepo.delete(category);
        
        log.info("Category has been deleted successfully");
        
        return Response.builder()
                .status(200)
                .message("Category has been deleted successfully")
                .build();
    }
}