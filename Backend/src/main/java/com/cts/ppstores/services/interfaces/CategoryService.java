package com.cts.ppstores.services.interfaces;

import com.cts.ppstores.dtos.CategoryDto;
import com.cts.ppstores.dtos.Response;

public interface CategoryService {

    Response createCategory(CategoryDto categoryRequest);
    Response updateCategory(Long categoryId, CategoryDto categoryRequest);
    Response getAllCategories();
    Response getCategoryById(Long categoryId);
    Response deleteCategory(Long categoryId);
}