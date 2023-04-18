package med.voll.apiMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.apiMed.domain.consulta.AgendaDeConsultas;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
	
	@Autowired
	private AgendaDeConsultas agenda;
	
	@PostMapping
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
		
		agenda.agendar(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
	}

}
