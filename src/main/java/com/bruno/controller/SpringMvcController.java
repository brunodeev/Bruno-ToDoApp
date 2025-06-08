package com.bruno.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpringMvcController {

    @GetMapping("/hello")
    public String Hello(Model model) {
        model.addAttribute("message","Este é um teste de mensagem utilizando thymeleaf!");
        return "hello";
    }
}
