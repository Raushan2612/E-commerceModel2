package com.cts.PPStoresApplication.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cts.ppstores.controllers.AddressController;
import com.cts.ppstores.dtos.AddressDto;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.services.interfaces.AddressService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

    @Mock
    private AddressService addressService;
    
    @InjectMocks
    private AddressController addressController;

    @Test
    public void testSaveAndUpdateAddress() {
        AddressDto addressDto = new AddressDto();
        Response response = Response.builder().status(200).message("Address has been added successfully").build();

        when(addressService.saveAndUpdateAddress(addressDto)).thenReturn(response);

        ResponseEntity<Response> result = addressController.saveAndUpdateAddress(addressDto);

        assertEquals(200, result.getBody().getStatus());
        assertEquals("Address has been added successfully", result.getBody().getMessage());
    }
}