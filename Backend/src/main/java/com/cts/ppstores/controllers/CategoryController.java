package com.cts.ppstores.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.ppstores.dtos.CategoryDto;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.services.interfaces.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> createCategory(@RequestBody @Valid CategoryDto categoryDto){
        Response response = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllCategories(){
        Response response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryDto categoryDto){
        Response response = categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long categoryId){
        Response response = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-category-by-id/{categoryId}")
    public ResponseEntity<Response> getCategoryById(@PathVariable Long categoryId){
        Response response = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(response);
    }
}