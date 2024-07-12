package com.fc8.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/articles")
@Controller
public class ArticleController {

    @GetMapping(path = "")
    public String articles(ModelMap modelMap) {
        modelMap.addAttribute("articles", List.of());
        return "articles/index";
    }
}
