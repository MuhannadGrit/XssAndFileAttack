package com.example.XSS;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    //First method for demo
    @PostMapping("/test")
    public String test(@RequestParam String input, Model model) {
        model.addAttribute("message", input); // Sårbar för XSS
        return "index";
    }


    //Fix metoden
    /*
    @PostMapping("/test")
    public String test(@RequestParam String input, Model model) {
        String safeInput = input.replaceAll("<", "&lt;").replaceAll(">", "&gt;"); // Enkel sanering
       model.addAttribute("message", safeInput);
       return "index";
    }
     */
}