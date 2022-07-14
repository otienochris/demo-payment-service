package com.example.paymentservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 05/07/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlLink {
    private String link;
}
