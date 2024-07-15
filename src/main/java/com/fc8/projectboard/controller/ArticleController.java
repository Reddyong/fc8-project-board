package com.fc8.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(path = "/{article-id}")
    public String articles(
            ModelMap modelMap,
            @PathVariable(name = "article-id")
            Long articleId
    ) {

        modelMap.addAttribute("article", "article");    // TODO : 구현 할때는 여기에 나중에 실제 데이터 넣어줘야함.
        modelMap.addAttribute("comments", List.of());
        return "articles/detail";
    }
}
