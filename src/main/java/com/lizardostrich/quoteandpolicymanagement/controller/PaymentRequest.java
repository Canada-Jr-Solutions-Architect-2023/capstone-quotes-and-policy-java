package com.lizardostrich.quoteandpolicymanagement.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long id;
    private boolean payment_status;
}
