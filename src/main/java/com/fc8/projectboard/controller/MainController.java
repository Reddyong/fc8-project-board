package com.fc8.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(path = "/")
    public String root() {
        return "forward:/articles";
    }
}
