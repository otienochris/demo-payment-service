package com.example.paymentservice.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 05/07/2022
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "paypal")
public class PaypalConfigProperties {
    private String mode;
    private String clientId;
    private String clientSecret;
    private String redirectCancel;
    private String redirectReturn;
//    // eEVf<9>S
}
