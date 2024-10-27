package com.example.demo.controller;

import com.example.demo.service.StringCalculatorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculator")
public class StringCalculatorController {

    private final StringCalculatorService service;

    public StringCalculatorController(StringCalculatorService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public int add(@RequestBody String numbers) throws Exception {
        return service.add(numbers);
    }

    @GetMapping("/count")
    public int getCallCount() {
        return service.getCallCount();
    }
}
