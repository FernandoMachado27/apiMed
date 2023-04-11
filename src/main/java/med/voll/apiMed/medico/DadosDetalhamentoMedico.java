package med.voll.apiMed.medico;

import med.voll.apiMed.endereco.Endereco;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, Endereco endereco) { // recebe os dados do medico

	public DadosDetalhamentoMedico(Medico medico) { // devolve os dados do medico, sem passar diretamente o medico no controller
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());  
	}
	
}
