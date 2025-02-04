package com.cts.ppstores.services.interfaces;

import com.cts.ppstores.dtos.AddressDto;
import com.cts.ppstores.dtos.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);
}