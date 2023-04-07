package med.voll.apiMed.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
		@NotBlank
		String logradouro, 
		@NotBlank
		String bairro, 
		@NotBlank
		@Pattern(regexp = "\\d{8}") // deve ter 8 digitos
		String cep, 
		@NotBlank
		String cidade, 
		@NotBlank
		String uf, 
		String complemento,
		String numero) { // DTO, model transfere dados entre camadas

}
