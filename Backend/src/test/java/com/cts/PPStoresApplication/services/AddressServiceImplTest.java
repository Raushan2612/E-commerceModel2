package com.cts.PPStoresApplication.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.ppstores.dtos.AddressDto;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.models.Address;
import com.cts.ppstores.models.User;
import com.cts.ppstores.repositories.AddressRepo;
import com.cts.ppstores.services.implementations.AddressServiceImpl;
import com.cts.ppstores.services.interfaces.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressServiceImpl;

    @Mock
    private AddressRepo addressRepo;

    @Mock
    private UserService userService;
    
    private AddressDto addressDto;
    private User user;
    private Address address;

    @BeforeEach
    void setUp() {
        addressDto = new AddressDto();
        addressDto.setStreet("123 Main St");
        addressDto.setCity("Springfield");
        addressDto.setState("IL");
        addressDto.setZipCode("62701");
        addressDto.setCountry("USA");

        user = new User();
        address = new Address();
        
        when(userService.getLoginUser()).thenReturn(user);
        when(addressRepo.save(any(Address.class))).thenReturn(address);
    }

    @Test
    void testSaveAndUpdateAddress_NewAddress() {
    	
        Response response = addressServiceImpl.saveAndUpdateAddress(addressDto);
        
        assertEquals(200, response.getStatus());
        assertEquals("Address has been added successfully", response.getMessage());
        verify(addressRepo, times(1)).save(any(Address.class));
    }

    @Test
    void testSaveAndUpdateAddress_UpdateAddress() {
    	user.setAddress(address);
        Response response = addressServiceImpl.saveAndUpdateAddress(addressDto);

        assertEquals(200, response.getStatus());
        assertEquals("Address has been updated successfully", response.getMessage());
        verify(addressRepo, times(1)).save(any(Address.class));
    }
}