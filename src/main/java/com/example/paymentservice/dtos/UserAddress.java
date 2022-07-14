package com.example.paymentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 05/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {
    private String street;
    private String postalCode;
    private String city;
    private String countryCode;
    private String country;
}