package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("message", "Authenticated! You are seeing a protected page.");
    return "index";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/public")
  public String publicPage(Model model) {
    model.addAttribute("message", "This page is public.");
    return "public";
  }
}
