package com.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void all() {}


    @GetMapping("/user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void user() {}


    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void admin() {}
}
