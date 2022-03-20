package com.baca.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {
	
	@RequestMapping("/Please")
	public String home(){
		return "ShoeGenerator.jsp";
	}

}
