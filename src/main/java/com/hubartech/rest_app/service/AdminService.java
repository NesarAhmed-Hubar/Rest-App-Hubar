package com.hubartech.rest_app.service;

import com.hubartech.rest_app.model.Address;
import com.hubartech.rest_app.model.User;
import com.hubartech.rest_app.model.UserInfo;
import com.hubartech.rest_app.repository.AddressRepository;
import com.hubartech.rest_app.repository.UserInfoAddressRepository;
import com.hubartech.rest_app.repository.UserInfoRepository;
import com.hubartech.rest_app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final AddressRepository addressRepository;
    private final UserInfoAddressRepository userInfoAddressRepository;

    public AdminService(UserRepository userRepository, UserInfoRepository userInfoRepository,
                        AddressRepository addressRepository, UserInfoAddressRepository userInfoAddressRepository) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.addressRepository = addressRepository;
        this.userInfoAddressRepository = userInfoAddressRepository;
    }

    public ResponseEntity<?> getAll() {
        List<User> userList = userRepository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteUser(Integer userId) {
        HashMap<String, Object> response = new HashMap<>();
        User user = userRepository.findById(userId);
        if (user != null) {
            UserInfo userInfo = userInfoRepository.findByUser(user);
            List<Address> addressList = userInfo.getAddressList();
            for (Address address : addressList) {
                addressRepository.delete(address.getId());
                userInfoAddressRepository.delete(userInfo.getId(), address.getId());
            }
            userInfoRepository.delete(userInfo.getId());
            userRepository.delete(user.getId());
            response.put("message", "User deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
