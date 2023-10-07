package com.example.libraryapi.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class UserController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is secure";
    }

    @GetMapping("/serq")
    public String serq() {
        return "ganster";
    }

}