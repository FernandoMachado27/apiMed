package med.voll.apiMed.domain.consulta;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.apiMed.domain.medico.Especialidade;

public record DadosAgendamentoConsulta(
		Long idMedico,
		@NotNull
		Long idPaciente,
		@NotNull
		@Future // apenas datas futuras
		LocalDateTime data,
		Especialidade especialidade) {

}
