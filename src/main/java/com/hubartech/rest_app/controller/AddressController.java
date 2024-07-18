//package com.hubartech.rest_app.controller;
//
//import com.hubartech.rest_app.dto.AddressDto;
//import com.hubartech.rest_app.service.AddressService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/address")
//public class AddressController {
//
//    private final AddressService addressService;
//
//    public AddressController(AddressService addressService) {
//        this.addressService = addressService;
//    }
//
//    @PreAuthorize("hasAuthority('USER')")
//    @GetMapping("/all")
//    public ResponseEntity<?> getAllAddress(Authentication authentication) {
//        return addressService.getMyAddress(authentication);
//    }
//
//    @PreAuthorize("hasAuthority('USER')")
//    @PostMapping("/add")
//    public ResponseEntity<?> postAddress(@RequestBody AddressDto addressDto, Authentication authentication) {
//        return addressService.addAddress(addressDto, authentication);
//    }
//}