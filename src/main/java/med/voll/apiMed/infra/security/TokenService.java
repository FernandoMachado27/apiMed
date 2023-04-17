package med.voll.apiMed.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import med.voll.apiMed.domain.usuario.Usuario;

@Service // spring starta automaticamente
public class TokenService {
	
	public String gerarToken(Usuario usuario) {
		try {
		    var algoritmo = Algorithm.HMAC256("12345678"); // algoritmo que faz assinatura digital, passando senha da API
		    return JWT.create()
		        .withIssuer("API Voll.med") // quem gera esse token
		        .withSubject(usuario.getLogin()) // pessoa relacionada a este token
		        .withExpiresAt(dataExpiracao())
		        .sign(algoritmo);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro ao gerar token jwt", exception);
		}
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // 2 horas de duração com a hora do Brasil
	}

}
