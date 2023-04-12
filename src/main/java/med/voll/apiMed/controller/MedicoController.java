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
import med.voll.apiMed.domain.medico.DadosAtualizaçaoMedico;
import med.voll.apiMed.domain.medico.DadosCadastroMedico;
import med.voll.apiMed.domain.medico.DadosDetalhamentoMedico;
import med.voll.apiMed.domain.medico.DadosListagemMedico;
import med.voll.apiMed.domain.medico.Medico;
import med.voll.apiMed.domain.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired // Spring que instancia
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional // transação ativa com BD
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) { // classe do Spring que cria URI (no caso a minha é localhost8080)
		var medico = new Medico(dados);
		repository.save(medico);
		
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); // cria objeto uri passando o path
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){ // criamos o DadosListagem para devolver apenas alguns atributos dos médicos, e adicionamos a paginação
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); // converter de medico p/ DadosListagem, listando apenas ativos
		
		return ResponseEntity.ok(page); // retorna 200 com o objeto de paginação
	}
	
	@PutMapping
	@Transactional // JPA vai fazer o update sozinho 
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizaçaoMedico dados) {
		var medico = repository.getReferenceById(dados.id()); // carrega o medico pelo id 
		medico.atualizarInformacoes(dados); // passa novos dados para o medico
		
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); // cria um DTO para devolver todas as informações do médico atualizado
	}
	
	@DeleteMapping("/{id}") // parametro dinamico, pode variar, vindo da URL
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) { // variavel do caminho, da URL, apenas inativa usuário
		var medico = repository.getReferenceById(id);
		medico.excluir();
		
		return ResponseEntity.noContent().build(); // retorna 204 sem corpo
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity detalhar(@PathVariable Long id) {  // buscar um medico pelo id
		var medico = repository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); 
	}

}
