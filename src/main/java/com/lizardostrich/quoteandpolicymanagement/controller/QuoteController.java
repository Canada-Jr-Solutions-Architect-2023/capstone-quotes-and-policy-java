package com.lizardostrich.quoteandpolicymanagement.controller;

import com.lizardostrich.quoteandpolicymanagement.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quote")
public class QuoteController {

    private QuoteService quoteService;
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping()
    public ResponseEntity<Double> generateQuote(@RequestBody QuoteRequest quoteRequest){
        return this.quoteService.generateQuote(quoteRequest);
    }
}
