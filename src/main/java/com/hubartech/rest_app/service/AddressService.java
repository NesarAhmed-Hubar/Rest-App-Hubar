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

    public AddressService(UserInfoRepository userInfoRepository, AddressRepository addressRepository,
                          UserInfoAddressRepository userInfoAddressRepository) {
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

    public ResponseEntity<?> deleteAddressById(Integer addressId, Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        User user = (User) authentication.getPrincipal();
        UserInfo userInfo = userInfoRepository.findByUser(user);

        boolean myAddress = false;
        for (Address address : userInfo.getAddressList()) {
            if (address.getId().equals(addressId)){
                myAddress = true;
                break;
            }
        }

        if(myAddress) {
            addressRepository.delete(addressId);
            userInfoAddressRepository.delete(userInfo.getId(), addressId);
            response.put("massage", "Address deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        else{
            response.put("massage", "This address not belongs to you.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateAddressById(AddressDto addressDto, Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        if (addressDto.getId() == null) {
            response.put("massage", "Address not found, please provide id.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = (User) authentication.getPrincipal();
        UserInfo userInfo = userInfoRepository.findByUser(user);
        boolean myAddress = false;
        for (Address address : userInfo.getAddressList()) {
            if (address.getId().equals(addressDto.getId())) {
                myAddress = true;
                break;
            }
        }

        if(myAddress) {
            Address address = addressRepository.FindById(addressDto.getId());
            if (addressDto.getLine1() != null) address.setLine1(addressDto.getLine1());
            if (addressDto.getLine2() != null) address.setLine2(addressDto.getLine2());
            if (addressDto.getCity() != null) address.setCity(addressDto.getCity());
            if (addressDto.getState() != null) address.setState(addressDto.getState());
            if (addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());
            if (addressDto.getPostalCode() != null) address.setPostalCode(addressDto.getPostalCode());
            addressRepository.update(address);
            response.put("massage", "Address updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        else {
            response.put("massage", "This address not belongs to you.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}