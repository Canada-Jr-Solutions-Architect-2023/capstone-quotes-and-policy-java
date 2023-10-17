package com.lizardostrich.quoteandpolicymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QuoteAndPolicyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoteAndPolicyManagementApplication.class, args);
	}

}
