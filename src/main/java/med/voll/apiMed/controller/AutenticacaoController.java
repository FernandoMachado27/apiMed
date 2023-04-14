package med.voll.apiMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.apiMed.domain.usuario.DadosAutenticacao;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager; // n podemos chamar o autenticationService diretamente
	
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); // tem que passar pelo proprio DTO do Spring
		var autentication = manager.authenticate(token); // Spring encontra a classe AutenticacaoService, chama o metodo que usa o repository, fazendo a consulta no BD
		
		return ResponseEntity.ok().build();
	}

}
