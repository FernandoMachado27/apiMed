package med.voll.apiMed.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros { // classe que isola o tratamento de exceções da API

	@ExceptionHandler(EntityNotFoundException.class) // onde for lançada esta exception
	public ResponseEntity tratarErro404() {
		return ResponseEntity.notFound().build();
	}
	
}
