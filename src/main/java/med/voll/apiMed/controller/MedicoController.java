package med.voll.apiMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.apiMed.medico.DadosCadastroMedico;
import med.voll.apiMed.medico.DadosListagemMedico;
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
	
	@GetMapping
	public Page<DadosListagemMedico> listar(Pageable paginacao){ // criamos o DadosListagem para devolver apenas alguns atributos dos médicos
		return repository.findAll(paginacao).map(DadosListagemMedico::new); // converter de medico p/ DadosListagem
	}

}
