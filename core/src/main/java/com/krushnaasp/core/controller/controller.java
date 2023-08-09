package com.krushnaasp.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("core")
public class controller {
    @GetMapping("/givesomething")
    public String giveSomething(){
        System.out.println("Controller Executed");
        return "Test Message";
    }
}
