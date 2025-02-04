package com.cts.ppstores.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ppstores.dtos.AddressDto;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.services.interfaces.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
    private AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveAndUpdateAddress(@RequestBody @Valid AddressDto addressDto){
    	Response response = addressService.saveAndUpdateAddress(addressDto);
        return ResponseEntity.ok(response);
    }
}