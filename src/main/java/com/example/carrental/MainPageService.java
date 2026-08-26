package com.example.carrental;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainPageService {
    @GetMapping("/")
    public String hello() {
        return "<h1 style='text-align: center;'>Main Page<h1>";
    }
}