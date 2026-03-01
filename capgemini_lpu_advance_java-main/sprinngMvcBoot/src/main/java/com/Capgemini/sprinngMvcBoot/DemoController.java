package com.Capgemini.sprinngMvcBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DemoController {
	@Autowired
	UserJpaRepository jpa;
	
	@GetMapping("/hello")
	public String getHi() {
		return "welcome";
	}

	@GetMapping("/register")
	public String createAccount() {
		return "register";
	}
	
	
//	@PostMapping("/create-account")
//	public String register(HttpServletRequest request) {
//		String name=request.getParameter("name");
//		String email=request.getParameter("email");
//		String number=request.getParameter("number");
//		
//		System.out.println(name);
//		System.out.println(email);
//		System.out.println(number);
//		return "sucsses";
//		
//	}
	
	
//	@PostMapping("/create-account")
//	public String register(@ModelAttribute User user) {
//		System.out.println(user.getName());
//		System.out.println(user.getEmail());
//		System.out.println(user.getNumber());
//		return "sucsses";
//	}
	
	@PostMapping("/create-account")
	public String register(@ModelAttribute User user) {
		 jpa.save(user);
		return "sucsses";
	}
	
}
