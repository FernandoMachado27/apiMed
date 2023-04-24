package med.voll.apiMed.domain.medico;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest // utilizada para testar uma interface repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // não substituiir meu bd pelo que está em memória
@ActiveProfiles("test") // para ler aplication-test
class MedicoRepositoryTest {

	@Test
	void escolherMedicoAleatorioLivreNaData() {
		
	}

}
