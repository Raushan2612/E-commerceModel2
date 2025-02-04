package com.cts.ppstores.services.interfaces;

import java.math.BigDecimal;

import com.cts.ppstores.dtos.ProductDto;
import com.cts.ppstores.dtos.Response;

public interface ProductService {

    Response createProduct(Long categoryId, ProductDto productDto);
    Response updateProduct(Long productId, Long categoryId, String image, String name, String description, BigDecimal price);
    Response deleteProduct(Long productId);
    Response getProductById(Long productId);
    Response getAllProducts();
    Response getProductsByCategory(Long categoryId);
    Response searchProduct(String searchValue);
}