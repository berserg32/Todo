package com.example.todo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Todo!";
    }

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/hello/{name}")
    public String helloByName(@PathVariable String name){
        return "Hello, " + name + "!";
    }
}