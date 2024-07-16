package com.hubartech.rest_app.repository;

import com.hubartech.rest_app.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
