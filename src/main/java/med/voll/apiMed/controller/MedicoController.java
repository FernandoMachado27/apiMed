package med.voll.apiMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.apiMed.medico.DadosCadastroMedico;
import med.voll.apiMed.medico.Medico;
import med.voll.apiMed.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired // Spring que instancia
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional // transação ativa com BD
	public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		var medico = new Medico(dados);
		repository.save(medico);
	}

}
