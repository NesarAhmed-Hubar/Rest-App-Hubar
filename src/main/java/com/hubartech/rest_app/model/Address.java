package com.hubartech.rest_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer id;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}