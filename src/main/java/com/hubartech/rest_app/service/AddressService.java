package com.hubartech.rest_app.service;

import com.hubartech.rest_app.dto.AddressDto;
import com.hubartech.rest_app.model.Address;
import com.hubartech.rest_app.model.User;
import com.hubartech.rest_app.model.UserInfo;
import com.hubartech.rest_app.repository.AddressRepository;
import com.hubartech.rest_app.repository.UserInfoAddressRepository;
import com.hubartech.rest_app.repository.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AddressService {

    private final UserInfoRepository userInfoRepository;
    private final AddressRepository addressRepository;
    private final UserInfoAddressRepository userInfoAddressRepository;

    public AddressService(UserInfoRepository userInfoRepository, AddressRepository addressRepository, UserInfoAddressRepository userInfoAddressRepository) {
        this.userInfoRepository = userInfoRepository;
        this.addressRepository = addressRepository;
        this.userInfoAddressRepository = userInfoAddressRepository;
    }

    public ResponseEntity<?> getMyAddress(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserInfo userInfo = userInfoRepository.findByUser(user);
        return new ResponseEntity<>(userInfo.getAddressList(), HttpStatus.OK);
    }

    public ResponseEntity<?> addAddress(AddressDto addressDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserInfo userInfo = userInfoRepository.findByUser(user);

        Address address = new Address();
        address.setLine1(addressDto.getLine1());
        address.setLine2(addressDto.getLine2());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCountry(addressDto.getCountry());
        address.setPostalCode(addressDto.getPostalCode());

        Integer addressId = addressRepository.create(address);
        userInfoAddressRepository.create(userInfo.getId(), addressId);

        HashMap<String, Object> response = new HashMap<>();
        response.put("massage", "Address added successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}