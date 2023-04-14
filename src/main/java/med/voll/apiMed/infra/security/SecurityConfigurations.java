package med.voll.apiMed.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // classe de configuração
@EnableWebSecurity // vamos personalizar config de segurança
public class SecurityConfigurations {
	
	@Bean // devolve obj p/ spring
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable() // desabilitar ataque csrf pois o token já protege
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().build(); // desabilita autenticação do form, e a autenticação seja Stateless por ser REST
	}

}
