package med.voll.apiMed.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.apiMed.controller.DadosAgendamentoConsulta;
import med.voll.apiMed.domain.ValidacaoException;
import med.voll.apiMed.domain.medico.Medico;
import med.voll.apiMed.domain.medico.MedicoRepository;
import med.voll.apiMed.domain.paciente.PacienteRepository;

@Service 
public class AgendaDeConsultas {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	public void agendar(DadosAgendamentoConsulta dados) {
		if (!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("Id do paciente não existe!");
		}
		
		if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) { // medico opcional
			throw new ValidacaoException("Id do médico não existe!");
		}
		
		var paciente = pacienteRepository.findById(dados.idPaciente()).get(); // .get() para pegar o obj e não o optional
		var medico = escolherMedico(dados);
		var consulta = new Consulta(null, medico, paciente, dados.data());
		consultaRepository.save(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		return null;
		
	}

}
