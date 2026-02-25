package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PublicController {

  @GetMapping("/public")
  @ResponseBody
  public String publicPage() {
    return "Public page (no login required)";
  }
}