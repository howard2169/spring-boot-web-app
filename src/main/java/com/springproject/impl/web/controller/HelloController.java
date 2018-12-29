package com.springproject.impl.web.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class HelloController {

    @RequestMapping(path = "hello", method = RequestMethod.GET)
    public ResponseEntity hello() {
        return ResponseEntity.noContent().build();
    }
}
