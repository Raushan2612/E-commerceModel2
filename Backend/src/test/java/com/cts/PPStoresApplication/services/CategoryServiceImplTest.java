package com.cts.PPStoresApplication.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.ppstores.controllers.mappers.EntityDtoMapper;
import com.cts.ppstores.dtos.CategoryDto;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.exceptions.DuplicateEntryException;
import com.cts.ppstores.exceptions.NotFoundException;
import com.cts.ppstores.models.Category;
import com.cts.ppstores.repositories.CategoryRepo;
import com.cts.ppstores.services.implementations.CategoryServiceImpl;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private EntityDtoMapper entityDtoMapper;

    @Test
    public void testCreateCategory_DuplicateEntry() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Electronics");
        
        Category existingCategory = new Category();
        when(categoryRepo.findByName(categoryDto.getName())).thenReturn(existingCategory);

        assertThrows(DuplicateEntryException.class, () -> categoryService.createCategory(categoryDto));
    }

    @Test
    public void testCreateCategory_Success() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Electronics");

        when(categoryRepo.findByName(categoryDto.getName())).thenReturn(null);
        when(categoryRepo.save(any(Category.class))).thenReturn(new Category());

        Response response = categoryService.createCategory(categoryDto);

        assertEquals(200, response.getStatus());
        assertEquals("Category has been created successfully", response.getMessage());
    }

    @Test
    public void testUpdateCategory_NotFound() {
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto();

        when(categoryRepo.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.updateCategory(categoryId, categoryDto));
    }

    @Test
    public void testUpdateCategory_DuplicateEntry() {
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Electronics");

        Category existingCategory = new Category();
        existingCategory.setId(2L);

        when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(new Category()));
        when(categoryRepo.findByName(categoryDto.getName())).thenReturn(existingCategory);

        assertThrows(DuplicateEntryException.class, () -> categoryService.updateCategory(categoryId, categoryDto));
    }

    @Test
    public void testUpdateCategory_Success() {
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Electronics");

        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepo.findByName(categoryDto.getName())).thenReturn(null);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);

        Response response = categoryService.updateCategory(categoryId, categoryDto);

        assertEquals(200, response.getStatus());
        assertEquals("category has been updated successfully", response.getMessage());
    }

    @Test
    public void testGetAllCategories() {
        when(categoryRepo.findAll()).thenReturn(Collections.emptyList());

        Response response = categoryService.getAllCategories();

        assertEquals(200, response.getStatus());
        assertTrue(response.getCategoryList().isEmpty());
    }

    @Test
    public void testGetCategoryById_NotFound() {
        Long categoryId = 1L;

        when(categoryRepo.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @Test
    public void testGetCategoryById_Success() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        when(entityDtoMapper.mapCategoryToDtoBasic(category)).thenReturn(new CategoryDto());

        Response response = categoryService.getCategoryById(categoryId);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testDeleteCategory_NotFound() {
        Long categoryId = 1L;

        when(categoryRepo.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.deleteCategory(categoryId));
    }

    @Test
    public void testDeleteCategory_Success() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));

        Response response = categoryService.deleteCategory(categoryId);

        assertEquals(200, response.getStatus());
        assertEquals("Category has been deleted successfully", response.getMessage());
        verify(categoryRepo, times(1)).delete(category);
    }
}