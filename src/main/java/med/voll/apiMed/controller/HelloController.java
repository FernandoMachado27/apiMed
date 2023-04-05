package med.voll.apiMed.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // classe que mapeia as requisições que vão chegar na API
@RequestMapping("/hello")
public class HelloController {
	
	@GetMapping
	public String olaMundo() {
		return "Hello World Spring";
	}

}
