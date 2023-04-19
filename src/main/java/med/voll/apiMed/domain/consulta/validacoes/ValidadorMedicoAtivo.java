package med.voll.apiMed.domain.consulta.validacoes;

import med.voll.apiMed.controller.DadosAgendamentoConsulta;
import med.voll.apiMed.domain.ValidacaoException;
import med.voll.apiMed.domain.medico.MedicoRepository;

public class ValidadorMedicoAtivo {
	
	private MedicoRepository repository;
	
	public void validar(DadosAgendamentoConsulta dados) {
		// escolha do medico opcional 
		if (dados.idMedico() == null) {
			return;
		}
		
		var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
		if (!medicoEstaAtivo) {
			throw new ValidacaoException("Consulta não pode ser agendada com médico inativo");
		}
	}

}
