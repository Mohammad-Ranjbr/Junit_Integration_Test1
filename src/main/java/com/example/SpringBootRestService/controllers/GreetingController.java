package com.example.SpringBootRestService.controllers;

import com.example.SpringBootRestService.models.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1")
public class GreetingController {

    private final Greeting greeting;
    private final AtomicLong counter = new AtomicLong(0);

    @Autowired
    public GreetingController(Greeting greeting){
        this.greeting = greeting;
    }
    @GetMapping("/greeting")
    public ResponseEntity<Greeting> greeting(@RequestParam("name") String name){
        greeting.setId(counter.incrementAndGet());
        greeting.setContent("Hey I am learning Spring Boot from " + name);
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }

    @GetMapping("/greeting/{name}")
    public ResponseEntity<Greeting> greeting1(@PathVariable("name") String name){
        greeting.setId(counter.incrementAndGet());
        greeting.setContent("Hey I am learning Spring Boot from " + name);
        return new ResponseEntity<>(greeting,HttpStatus.OK);
    }

}
