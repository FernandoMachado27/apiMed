package med.voll.apiMed.medico;

import med.voll.apiMed.endereco.DadosEndereco;

public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade,
		DadosEndereco endereco) { // DTO, model

}
