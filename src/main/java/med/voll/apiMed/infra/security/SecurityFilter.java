package med.voll.apiMed.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.apiMed.domain.usuario.UsuarioRepository;

@Component // Spring carrega automaticamente, classe generica
public class SecurityFilter extends OncePerRequestFilter{ // classe que implementa o Filter, vai ser executada apenas 1 vez por requisição
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		var tokenJWT = recuperarToken(request); // recupero token do cabeçalho
		
		if (tokenJWT != null) { // se tem token no cabeçalho, faço a validação
			var subject = tokenService.getSubject(tokenJWT); // verifica se o token esta valido
			var usuario = repository.findByLogin(subject); // recupera o usuario atraves do token
			
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // como se fosse um DTO do spring que representa um usuario logado
			SecurityContextHolder.getContext().setAuthentication(authentication); // spring considere que o usuario está logado
			
		}
		
		
		filterChain.doFilter(request, response); // continue o fluxo da requisição, chama prox filtro
	}

	private String recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", ""); // substitui a palavra "Brearer" por nada
		}
		
		return null;
		
	} 

}
