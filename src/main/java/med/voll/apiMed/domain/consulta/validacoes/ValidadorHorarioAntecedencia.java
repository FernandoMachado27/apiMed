package med.voll.apiMed.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import med.voll.apiMed.controller.DadosAgendamentoConsulta;
import med.voll.apiMed.domain.ValidacaoException;

public class ValidadorHorarioAntecedencia {

	public void validar(DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();
		var agora = LocalDateTime.now();
		var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
		
		if (diferencaEmMinutos < 30) {
			throw new ValidacaoException("Consulta deve ser realizaada com antecedência mínima de 30 minutos");
		}
	}

}
