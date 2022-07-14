package com.example.paymentservice.dtos;

import com.example.paymentservice.enums.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 08/07/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatus {
    private PaymentStatusEnum status;
}
