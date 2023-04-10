package med.voll.apiMed.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.apiMed.endereco.DadosEndereco;

public record DadosAtualizaçaoMedico(
		@NotNull
		Long id, 
		String nome, 
		String telefone, 
		DadosEndereco endereco) {
}
