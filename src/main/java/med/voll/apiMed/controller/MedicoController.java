package med.voll.apiMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.apiMed.medico.DadosAtualizaçaoMedico;
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
	public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){ // criamos o DadosListagem para devolver apenas alguns atributos dos médicos
		return repository.findAll(paginacao).map(DadosListagemMedico::new); // converter de medico p/ DadosListagem
	}
	
	@PutMapping
	@Transactional // JPA vai fazer o update sozinho 
	public void atualizar(@RequestBody @Valid DadosAtualizaçaoMedico dados) {
		var medico = repository.getReferenceById(dados.id()); // carrega o medico pelo id 
		medico.atualizarInformacoes(dados); // passa novos dados para o medico
	}
	
	@DeleteMapping("/{id}") // parametro dinamico, pode variar
	@Transactional
	public void excluir(@PathVariable Long id) { // variavel do caminho, da URL
		repository.deleteById(id);
	}

}
