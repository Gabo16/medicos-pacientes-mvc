package com.springboot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		System.out.println("/");
		return "home";
	}
	
	@GetMapping("/home")
	public String homeRedirect() {
		System.out.println("home");
		return "redirect:/";
	}
	
}
