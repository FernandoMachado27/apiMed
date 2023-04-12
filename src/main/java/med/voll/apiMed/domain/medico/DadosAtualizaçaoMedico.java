package med.voll.apiMed.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.apiMed.domain.endereco.DadosEndereco;

public record DadosAtualizaçaoMedico(
		@NotNull
		Long id, 
		String nome, 
		String telefone, 
		DadosEndereco endereco) {
}
