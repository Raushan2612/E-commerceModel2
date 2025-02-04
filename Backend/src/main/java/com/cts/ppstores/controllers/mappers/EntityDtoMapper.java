package com.cts.ppstores.controllers.mappers;


import org.slf4j.*;
import org.springframework.stereotype.Component;

import com.cts.ppstores.dtos.*;
import com.cts.ppstores.models.*;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class EntityDtoMapper {


	public UserDto mapUserToDtoBasic(User user){
    	
    	log.debug("Mapping User entity to UserDto: {}", user);
    	
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setName(user.getName());
        return userDto;
    }


    public AddressDto mapAddressToDtoBasic(Address address){
    	
    	log.debug("Mapping Address entity to AddressDto: {}", address);
    	
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        return addressDto;
    }


    public CategoryDto mapCategoryToDtoBasic(Category category){
    	
    	log.debug("Mapping Category entity to CategoryDto: {}", category);
    	
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }


    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem){
    	
    	log.debug("Mapping OrderItem entity to OrderItemDto: {}", orderItem);
    	
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }


    public ProductDto mapProductToDtoBasic(Product product){
    	
    	log.debug("Mapping Product entity to ProductDto: {}", product);
    	
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }

    
    public UserDto mapUserToDtoPlusAddress(User user){

    	log.debug("Mapping User entity to UserDto with Address: {}", user);
    	
        UserDto userDto = mapUserToDtoBasic(user);
        if (user.getAddress() != null){
            AddressDto addressDto = mapAddressToDtoBasic(user.getAddress());
            userDto.setAddress(addressDto);
        }
        return userDto;
    }


    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem){
    	
    	log.debug("Mapping OrderItem entity to OrderItemDto with Product: {}", orderItem);
    	
        OrderItemDto orderItemDto = mapOrderItemToDtoBasic(orderItem);

        if (orderItem.getProduct() != null) {
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }


    public OrderItemDto mapOrderItemToDtoPlusProductAndUser(OrderItem orderItem){
    	
    	log.debug("Mapping OrderItem entity to OrderItemDto with Product and User: {}", orderItem);
    	
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);

        if (orderItem.getUser() != null){
            UserDto userDto = mapUserToDtoPlusAddress(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }


    public UserDto mapUserToDtoPlusAddressAndOrderHistory(User user) {
    	
    	log.debug("Mapping User entity to UserDto with Address and Order History: {}", user);
    	
        UserDto userDto = mapUserToDtoPlusAddress(user);

        if (user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
            List<OrderItemDto> orderItemDtos = new ArrayList<>();
            for (OrderItem orderItem : user.getOrderItemList()) {
                OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);
                orderItemDtos.add(orderItemDto);
            }
            userDto.setOrderItemList(orderItemDtos);
        }
        
        return userDto;
    }
}