package med.voll.apiMed.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // classe de configuração
@EnableWebSecurity // vamos personalizar config de segurança
public class SecurityConfigurations {
	
	@Bean // devolve obj p/ spring, ensina ele a como criar um obj que posso injetar em algum lugar, ou que ele usa internamente
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable() // desabilitar ataque csrf pois o token já protege
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().build(); // desabilita autenticação do form, e a autenticação seja Stateless por ser REST
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception { // classe que sabe criar obj authenticationManager, para usar o AutenticacaoService
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // ensina ao eclipse que é para usar este algoritmo de senha
	}

}
