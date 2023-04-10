package med.voll.apiMed.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.apiMed.endereco.DadosEndereco;

public record DadosCadastroMedico(
		@NotBlank // verifica se não é nulo nem vazio, NotBlank é só para Strings
		String nome, 
		@NotBlank 
		@Email
		String email, 
		@NotBlank
		String telefone,
		@NotBlank
		@Pattern(regexp = "\\d{4,6}") // expressão regular de 4 a 6 digitos
		String crm, 
		@NotNull
		Especialidade especialidade,
		@NotNull
		@Valid // também validar dentro da classe abaixo
		DadosEndereco endereco) { // DTO, model, transfere dados entre camadas, recebe e devolve(GET/SET)

}
