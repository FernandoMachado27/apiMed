package med.voll.apiMed.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.apiMed.domain.consulta.AgendaDeConsultas;
import med.voll.apiMed.domain.consulta.DadosAgendamentoConsulta;
import med.voll.apiMed.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.apiMed.domain.medico.Especialidade;

@SpringBootTest // para fazer a simulação da classe controller
@AutoConfigureMockMvc
@AutoConfigureJsonTesters // p/ injetar Jackson Tester
class ConsultaControllerTest {
	
	@Autowired
	private MockMvc mvc; // simula requisição MVC, HTTP
	
	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;
	
	@MockBean // quando precisar injetar, faça um mock dele (senão ele usa nosso BD)
	private AgendaDeConsultas agendaDeConsultas;

	@Test
	@DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
	@WithMockUser // considere usuario logado
	void agendar_cenario1() throws Exception {
		var response = mvc.perform(post("/consultas")).andReturn().getResponse(); // dispare uma req post p/ metodo de consultas, req sem corpo
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); // verifico erro 400
	}
	
	@Test
	@DisplayName("Deveria devolver código http 200 quando informações estão válidas")
	@WithMockUser 
	void agendar_cenario2() throws Exception {
		var data = LocalDateTime.now().plusHours(1);
		var especialidade = Especialidade.CARDIOLOGIA;
		
		var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 3l, 7l, data);
		when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento); // quando chamar agendar, não importa o que for passado, retorne o DTO
		
		var response = mvc
				.perform(
						post("/consultas")
							.contentType(MediaType.APPLICATION_JSON)
							.content(dadosAgendamentoConsultaJson.write(
									new DadosAgendamentoConsulta(3l, 7l, data, especialidade)
							).getJson()) // pega o objeto e converte em Json
				)
				.andReturn().getResponse(); 
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); 
		
		var jsonEsperado = dadosDetalhamentoConsultaJson.write(
					dadosDetalhamento
				).getJson();
				
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

}
