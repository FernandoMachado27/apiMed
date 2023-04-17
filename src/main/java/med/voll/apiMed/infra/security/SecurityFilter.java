package med.voll.apiMed.infra.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Spring carrega automaticamente, classe generica
public class SecurityFilter extends OncePerRequestFilter{ // classe que implementa o Filter, vai ser executada apenas 1 vez por requisição

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		filterChain.doFilter(request, response); // continue o fluxo da requisição, chama prox filtro
	} 

}
