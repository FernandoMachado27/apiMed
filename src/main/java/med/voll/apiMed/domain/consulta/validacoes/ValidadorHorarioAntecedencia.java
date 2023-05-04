package med.voll.apiMed.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.apiMed.domain.ValidacaoException;
import med.voll.apiMed.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta{

	public void validar(DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();
		var agora = LocalDateTime.now();
		var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
		
		if (diferencaEmMinutos < 30) {
			throw new ValidacaoException("Consulta deve ser realizaada com antecedência mínima de 30 minutos");
		}
	}

}
