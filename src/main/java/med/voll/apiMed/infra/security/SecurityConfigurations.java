package med.voll.apiMed.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // classe de configuração
@EnableWebSecurity // vamos personalizar config de segurança
public class SecurityConfigurations {
	
	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean // devolve obj p/ spring, ensina ele a como criar um obj que posso injetar em algum lugar, ou que ele usa internamente
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable() // desabilitar ataque csrf pois o token já protege
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeHttpRequests() 
				.requestMatchers(HttpMethod.POST, "/login").permitAll()// libere requisição de login
				.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // endereços publicos para gerar doc do SpringDoc
				.anyRequest().authenticated() // as outras requisições é para barrar
				.and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // diz ao Spring qual filtro vem primeiro(a minha antes que a do Spring
				.build(); // desabilita autenticação do form, e a autenticação seja Stateless por ser REST
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception { // classe que sabe criar obj authenticationManager, para usar o AutenticacaoService
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // ensina ao eclipse que é para usar este algoritmo de senha, o BCrypt
	}

}
