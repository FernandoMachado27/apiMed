package med.voll.apiMed.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.apiMed.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
		@NotBlank(message = "Nome é obrigatório") // verifica se não é nulo nem vazio, NotBlank é só para Strings
		String nome, 
		@NotBlank(message = "Email é obrigatório")
		@Email (message = "Formato do email é inválido")
		String email, 
		@NotBlank(message = "Telefone é obrigatório")
		String telefone,
		@NotBlank(message = "CRM é obrigatório")
		@Pattern(regexp = "\\d{4,6}", message = "Formato do CRM é inválido") // expressão regular de 4 a 6 digitos
		String crm, 
		@NotNull(message = "Especialidade é obrigatória")
		Especialidade especialidade,
		@NotNull(message = "Dados do endereço são obrigatórios")
		@Valid // também validar dentro da classe abaixo
		DadosEndereco endereco) { // DTO, model, transfere dados entre camadas, recebe e devolve(GET/SET)

}
