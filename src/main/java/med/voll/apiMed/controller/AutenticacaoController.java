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
import med.voll.apiMed.domain.usuario.Usuario;
import med.voll.apiMed.infra.security.DadosTokenJWT;
import med.voll.apiMed.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager; // n podemos chamar o autenticationService diretamente
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		var autenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); // tem que passar pelo proprio DTO do Spring
		var autentication = manager.authenticate(autenticationToken); // Spring encontra a classe AutenticacaoService, chama o metodo que usa o repository, fazendo a consulta no BD
		
		var tokenJWT = tokenService.gerarToken((Usuario) autentication.getPrincipal()); // chama a classe que cria o token, passando usuario autenticado
		
		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); // devolve o token dentro de um DTO
	}

}
