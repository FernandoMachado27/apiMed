package med.voll.apiMed.domain.consulta.validacoes;

import java.time.DayOfWeek;

import med.voll.apiMed.controller.DadosAgendamentoConsulta;
import med.voll.apiMed.domain.ValidacaoException;

public class ValidadorHorarioFuncionamentoClinica {
	
	public void validar(DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();
		
		var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY); // verificar se a data esta vindo no domingo
		var antesDaAberturaDaClinica = dataConsulta.getHour() < 7; 
		var depoisDoEncerramentoDaClinica = dataConsulta.getHour() < 18;
		
		if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
			throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
		}
	}

}
