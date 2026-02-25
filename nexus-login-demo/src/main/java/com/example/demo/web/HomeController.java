package com.example.demo.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String landing(Model model, Principal principal) {
    model.addAttribute("username", principal != null ? principal.getName() : "Customer");
    return "index"; // templates/index.html
  }

  @GetMapping("/public")
  public String publicPage() {
    return "public"; // templates/public.html
  }
}