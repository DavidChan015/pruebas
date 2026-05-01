package com.darkcode.spring.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    //hola12345
    @GetMapping("/")
    public String holaMundo() {
        return "Hola Mundo 2";
    }
}
