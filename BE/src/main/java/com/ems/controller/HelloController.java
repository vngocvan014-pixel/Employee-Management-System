package com.ems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello EMS!";
    }

    @GetMapping("/hello")
    public String helloBackend() {
        return "Hello from EMS Backend!";
    }

}