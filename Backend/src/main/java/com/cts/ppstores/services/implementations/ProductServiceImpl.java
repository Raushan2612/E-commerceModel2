package com.cts.ppstores.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cts.ppstores.controllers.mappers.EntityDtoMapper;
import com.cts.ppstores.dtos.ProductDto;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.exceptions.NotFoundException;
import com.cts.ppstores.models.Category;
import com.cts.ppstores.models.Product;
import com.cts.ppstores.repositories.CategoryRepo;
import com.cts.ppstores.repositories.ProductRepo;
import com.cts.ppstores.services.interfaces.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response createProduct(Long categoryId, ProductDto productDto) {
    	
    	log.info("Creating product in category ID {}: {}", categoryId, productDto);
    	
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));

        Product product = new Product();
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());

        productRepo.save(product);
        
        log.info("Product created successfully: {}", product);
        
        return Response.builder()
                .status(200)
                .message("Product successfully created")
                .build();
    }

    @Override
    public Response updateProduct(Long productId, Long categoryId, String image, String name, String description, BigDecimal price) {
    	
    	log.info("Updating product ID {}: categoryId={}, image={}, name={}, description={}, price={}", 
                productId, categoryId, image, name, description, price);
    	
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product Not Found"));

        Category category = null;

        if(categoryId != null ){
             category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));
        }

        if (category != null) product.setCategory(category);
        if (name != null) product.setName(name);
        if (price != null) product.setPrice(price);
        if (description != null) product.setDescription(description);
        if (image != null) product.setImageUrl(image);

        productRepo.save(product);
        
        log.info("Product updated successfully: {}", product);
        
        return Response.builder()
                .status(200)
                .message("Product updated successfully")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {
    	
    	log.info("Deleting product ID {}", productId);
    	
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product Not Found"));
        productRepo.delete(product);
        
        log.info("Product deleted successfully: {}", product);
        
        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
    	
    	log.info("Fetching product by ID {}", productId);
    	
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product Not Found"));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);
        
        log.info("Fetched product: {}", productDto);
        
        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
    	
    	log.info("Fetching all products");
    	
    	List<Product> productList = productRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    	
        List<ProductDto> productDtoList = new ArrayList<>(); 
        for(Product product : productList) {
        	ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);
        	productDtoList.add(productDto);
        }
        
        log.info("Fetched all products: {}", productDtoList);
        
        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();

    }

    @Override
    public Response getProductsByCategory(Long categoryId) {
    	
    	log.info("Fetching products by category ID {}", categoryId);
    	
        List<Product> products = productRepo.findByCategoryId(categoryId);
        if(products.isEmpty()){
        	log.warn("No products found for category ID {}", categoryId);
            throw new NotFoundException("No Products found for this category");
        }
        List<ProductDto> productDtoList = new ArrayList<>();
  		for(Product product : products) {
        	ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);
        	productDtoList.add(productDto);
        }

        log.info("Fetched products by category ID {}: {}", categoryId, productDtoList);
        
        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
    	
    	log.info("Searching products with value {}", searchValue);
    	
        List<Product> products = productRepo.findByNameContainingOrDescriptionContaining(searchValue, searchValue);

        if (products.isEmpty()){
        	log.warn("No products found for search value {}", searchValue);
            throw new NotFoundException("No Products Found");
        }
        
        List<ProductDto> productDtoList = new ArrayList<>();
  		for(Product product : products) {
        	ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);
        	productDtoList.add(productDto);
        }
        
        log.info("Found products for search value {}: {}", searchValue, productDtoList);

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }
}