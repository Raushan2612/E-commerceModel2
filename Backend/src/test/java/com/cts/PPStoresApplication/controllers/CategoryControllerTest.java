package com.cts.PPStoresApplication.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cts.ppstores.controllers.CategoryController;
import com.cts.ppstores.dtos.CategoryDto;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.services.interfaces.CategoryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Test
    public void testCreateCategory() {
        CategoryDto categoryDto = new CategoryDto();
        Response response = Response.builder().status(200).message("Category has been created successfully").build();

        when(categoryService.createCategory(categoryDto)).thenReturn(response);

        ResponseEntity<Response> result = categoryController.createCategory(categoryDto);

        assertEquals(200, result.getBody().getStatus());
        assertEquals("Category has been created successfully", result.getBody().getMessage());
    }

    @Test
    public void testGetAllCategories() {
        Response response = Response.builder().status(200).build();

        when(categoryService.getAllCategories()).thenReturn(response);

        ResponseEntity<Response> result = categoryController.getAllCategories();

        assertEquals(200, result.getBody().getStatus());
    }

    @Test
    public void testUpdateCategory() {
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto();
        Response response = Response.builder().status(200).message("category has been updated successfully").build();

        when(categoryService.updateCategory(categoryId, categoryDto)).thenReturn(response);

        ResponseEntity<Response> result = categoryController.updateCategory(categoryId, categoryDto);

        assertEquals(200, result.getBody().getStatus());
        assertEquals("category has been updated successfully", result.getBody().getMessage());
    }

    @Test
    public void testDeleteCategory() {
        Long categoryId = 1L;
        Response response = Response.builder().status(200).message("Category has been deleted successfully").build();

        when(categoryService.deleteCategory(categoryId)).thenReturn(response);

        ResponseEntity<Response> result = categoryController.deleteCategory(categoryId);

        assertEquals(200, result.getBody().getStatus());
        assertEquals("Category has been deleted successfully", result.getBody().getMessage());
    }

    @Test
    public void testGetCategoryById() {
        Long categoryId = 1L;
        Response response = Response.builder().status(200).build();

        when(categoryService.getCategoryById(categoryId)).thenReturn(response);

        ResponseEntity<Response> result = categoryController.getCategoryById(categoryId);

        assertEquals(200, result.getBody().getStatus());
    }
}