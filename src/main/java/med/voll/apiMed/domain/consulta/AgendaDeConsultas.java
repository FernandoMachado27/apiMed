package med.voll.apiMed.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.apiMed.domain.ValidacaoException;
import med.voll.apiMed.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
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
	
	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadores; // pega todos os validadores que implementa a interface
	
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		if (!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("Id do paciente não existe!");
		}
		
		if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) { // medico opcional
			throw new ValidacaoException("Id do médico não existe!");
		}
		
		validadores.forEach(v -> v.validar(dados)); // percorre todos os elementos da lista e chama o validar();
		
		var paciente = pacienteRepository.getReferenceById(dados.idPaciente()); 
		var medico = escolherMedico(dados);
		
		if (medico == null) {
			throw new ValidacaoException("Não existe médico disponível nesta data");
		}
		
		var consulta = new Consulta(null, medico, paciente, dados.data());
		consultaRepository.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if (dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		
		if (dados.especialidade() == null) {
			throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
		}
		
		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
		
	}

}
