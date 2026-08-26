package com.example.carrental;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainPageService {
    @GetMapping("/")
    public String hello() {
        return "<div style='text-align: center;'><h1>Main Page</h1> <a href='/view/cars'>See all available cars</a></div>";
    }
}