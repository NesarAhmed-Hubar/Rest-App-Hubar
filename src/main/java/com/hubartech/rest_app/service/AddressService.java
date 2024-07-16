package com.hubartech.rest_app.service;

import com.hubartech.rest_app.dto.AddressDto;
import com.hubartech.rest_app.model.Address;
import com.hubartech.rest_app.model.User;
import com.hubartech.rest_app.model.UserInfo;
import com.hubartech.rest_app.repository.AddressRepository;
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

    public AddressService(UserInfoRepository userInfoRepository, AddressRepository addressRepository) {
        this.userInfoRepository = userInfoRepository;
        this.addressRepository = addressRepository;
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

        addressRepository.save(address);

        System.out.println(address);

        userInfo.getAddressList().add(address);
        userInfoRepository.save(userInfo);

        HashMap<String, Object> response = new HashMap<>();
        response.put("massage", "Address added successfully.");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
