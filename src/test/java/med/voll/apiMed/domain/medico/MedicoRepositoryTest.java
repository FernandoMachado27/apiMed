package med.voll.apiMed.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.apiMed.domain.consulta.Consulta;
import med.voll.apiMed.domain.endereco.DadosEndereco;
import med.voll.apiMed.domain.paciente.DadosCadastroPaciente;
import med.voll.apiMed.domain.paciente.Paciente;

@DataJpaTest // utilizada para testar uma interface repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // não substituiir meu bd pelo que está em memória
@ActiveProfiles("test") // para ler aplication-test
class MedicoRepositoryTest { // testes realizados com banco de dados distinto
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TestEntityManager em; // usado p/ testes

	@Test
	@DisplayName("Deveria devolver null quando unico medico cadastrado não está disponível na data") // descrever cenário de teste
	void escolherMedicoAleatorioLivreNaDataCenario1() {
		var proximaSegundaAs10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10,0); // pega a data atual e rode na prox segunda
		
		var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
		var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "25685209567");
		cadastrarConsulta(medico, paciente, proximaSegundaAs10);
		
		
		var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
		assertThat(medicoLivre).isNull();
	}
	
	@Test
	@DisplayName("Deveria devolver medico quando ele estiver disponivel na data") 
	void escolherMedicoAleatorioLivreNaDataCenario2() {
		var proximaSegundaAs10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10,0); // pega a data atual e rode na prox segunda
		
		var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
		
		
		var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
		assertThat(medicoLivre).isEqualTo(medico);
	}
	
	// metodos para usar nos testes
	
	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
	    em.persist(new Consulta(null, medico, paciente, data));
	}

	private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
	    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
	    em.persist(medico);
	    return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
	    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
	    em.persist(paciente);
	    return paciente;
	}

	private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
	    return new DadosCadastroMedico(
	            nome,
	            email,
	            "61999999999",
	            crm,
	            especialidade,
	            dadosEndereco()
	    );
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
	    return new DadosCadastroPaciente(
	            nome,
	            email,
	            "61999999999",
	            cpf,
	            dadosEndereco()
	    );
	}

	private DadosEndereco dadosEndereco() {
	    return new DadosEndereco(
	            "rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            "DF",
	            null,
	            null
	    );
	}

}
