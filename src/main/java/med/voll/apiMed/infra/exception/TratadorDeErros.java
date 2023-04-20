package med.voll.apiMed.infra.exception;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import med.voll.apiMed.domain.ValidacaoException;

@RestControllerAdvice
public class TratadorDeErros { // classe que isola o tratamento de exceções da API, spring chama automaticamente sempre que acontece uma exception

	@ExceptionHandler(EntityNotFoundException.class) // onde for lançada esta exception
	public ResponseEntity tratarErro404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) // argumento não valido, faltando
	public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors(); // retorna lista
		
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList()); // faz conversão de uma lista para outra
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity tratarErroBadCredentials() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity tratarErroAuthentication() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity tratarErroAcessoNegado() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity tratarErro500(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	private record DadosErroValidacao(String campo, String mensagem) { // DTO dentro da classe, só vai ser usada aqui
		
		public DadosErroValidacao(FieldError erro){
			this(erro.getField(), erro.getDefaultMessage()); // field da o nome do campo, e default da a mensagem pra aquele campo especifico
		}
		
	}
	
}
