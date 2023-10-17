package com.lizardostrich.quoteandpolicymanagement.feign;

import com.lizardostrich.quoteandpolicymanagement.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "capstone-customer-inventory-java", url="https://carehub-customer.ee-cognizantacademy.com")
public interface CustomerServiceProxy {
    @GetMapping("/customers/id/{id}")
    Customer getCustomerById(@PathVariable("id") Long id);
}
