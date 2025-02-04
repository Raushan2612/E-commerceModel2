package com.cts.ppstores.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ppstores.dtos.AddressDto;
import com.cts.ppstores.dtos.Response;
import com.cts.ppstores.models.Address;
import com.cts.ppstores.models.User;
import com.cts.ppstores.repositories.AddressRepo;
import com.cts.ppstores.services.interfaces.AddressService;
import com.cts.ppstores.services.interfaces.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepo addressRepo;
    private final UserService userService;

    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto) {
    	
    	log.info("Saving or updating address: {}", addressDto);
    	
        User user = userService.getLoginUser();
        
        Address address = user.getAddress();
        
        boolean flag = false;
        if (address == null){
        	flag = true;
            address = new Address();
            address.setUser(user);
        }
        
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setZipCode(addressDto.getZipCode());
        address.setCountry(addressDto.getCountry());

        addressRepo.save(address);

        String message = flag ? "Address has been added successfully" : "Address has been updated successfully";
        
        log.info(message);
        
        return Response.builder()
                .status(200)
                .message(message)
                .build();
    }
}