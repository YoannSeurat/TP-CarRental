package com.example.carrental.service;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainPageService {
    @GetMapping("/")
    public String hello() {
        return "<div style='text-align: center;'>" +
                "<h1>Main Page</h1>" +
                "<a href='/view/cars'>See all available cars</a> <br>" +
                "<a href='/view/persons'>See all registered persons</a>" +
                "</div>";
    }
}