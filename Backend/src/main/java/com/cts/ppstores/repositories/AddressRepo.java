package com.cts.ppstores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ppstores.models.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {
}