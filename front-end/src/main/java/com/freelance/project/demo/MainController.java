package com.freelance.project.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resources")
public class MainController {

  @GetMapping("/index")
  public String index() {
    return "home";
  }

  @GetMapping("/registration")
  public String registration(){
    return "registration";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }


}
