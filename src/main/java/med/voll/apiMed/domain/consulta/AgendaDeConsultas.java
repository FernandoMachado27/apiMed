package med.voll.apiMed.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.apiMed.controller.DadosAgendamentoConsulta;
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
		
		var medico = medicoRepository.findById(dados.idMedico()).get(); // .get() para pegar o obj e não o optional
		var paciente = pacienteRepository.findById(dados.idPaciente()).get();
		var consulta = new Consulta(null, medico, paciente, dados.data());
		consultaRepository.save(consulta);
	}

}
