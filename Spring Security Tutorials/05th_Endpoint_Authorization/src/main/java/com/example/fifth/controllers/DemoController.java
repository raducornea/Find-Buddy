package com.example.fifth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/demo")
    public String demo() {
        return "Demo!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
