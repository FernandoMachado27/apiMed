package med.voll.apiMed.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros { // classe que isola o tratamento de exceções da API

	@ExceptionHandler(EntityNotFoundException.class) // onde for lançada esta exception
	public ResponseEntity tratarErro404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) // argumento não valido, faltando
	public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors(); // retorna lista
		
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList()); // faz conversão de uma lista para outra
	}
	
	
	
	
	private record DadosErroValidacao(String campo, String mensagem) { // DTO dentro da classe, só vai ser usada aqui
		
		public DadosErroValidacao(FieldError erro){
			this(erro.getField(), erro.getDefaultMessage()); // field da o nome do campo, e default da a mensagem pra aquele campo especifico
		}
		
	}
	
}
