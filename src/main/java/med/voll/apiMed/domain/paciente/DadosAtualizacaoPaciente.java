package med.voll.apiMed.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.apiMed.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
		@NotNull
		Long id, 
		String nome, 
		String email, 
		String telefone, 
		DadosEndereco endereco) {
}
