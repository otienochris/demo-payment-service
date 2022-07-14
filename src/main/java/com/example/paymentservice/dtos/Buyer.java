package com.example.paymentservice.dtos;

import com.example.paymentservice.enums.AddressEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 05/07/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Map<AddressEnum, UserAddress> addresses = new HashMap<>();
}
