package med.voll.apiMed.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest // para fazer a simulação da classe controller
@AutoConfigureMockMvc
class ConsultaControllerTest {
	
	@Autowired
	private MockMvc mvc; // simula requisição MVC, HTTP

	@Test
	@DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
	@WithMockUser // considere usuario logado
	void agendar_cenario1() throws Exception {
		var response = mvc.perform(post("/consultas")).andReturn().getResponse(); // dispare uma req post p/ metodo de consultas, req sem corpo
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); // verifico erro 400
	}

}
