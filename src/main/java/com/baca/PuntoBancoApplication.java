package com.baca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SpringBootApplication
public class PuntoBancoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuntoBancoApplication.class, args);
	}
}
