package med.voll.apiMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.apiMed.paciente.DadosAtualizacaoPaciente;
import med.voll.apiMed.paciente.DadosCadastroPaciente;
import med.voll.apiMed.paciente.DadosDetalhamentoPaciente;
import med.voll.apiMed.paciente.DadosListagemPaciente;
import med.voll.apiMed.paciente.Paciente;
import med.voll.apiMed.paciente.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	PacienteRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
		var paciente = new Paciente(dados);
		repository.save(paciente);
		
		var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
	}
	
	@GetMapping
	public ResponseEntity <Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
		
		return ResponseEntity.ok(page);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody DadosAtualizacaoPaciente dados) {
		var paciente = repository.getReferenceById(dados.id());
		paciente.atualizarDados(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		var paciente = repository.getReferenceById(id);
		paciente.excuir();
		
		return ResponseEntity.noContent().build();
	}

}