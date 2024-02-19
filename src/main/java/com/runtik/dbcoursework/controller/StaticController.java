package com.runtik.dbcoursework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class StaticController {
    @GetMapping
    public String homePage() {
        return "index.html";
    }
}
