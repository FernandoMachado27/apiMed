package med.voll.apiMed.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long>{ // a classe e o tipo da chave primaria, substitui a classe DAO, acessa o BD

	Page<Medico> findAllByAtivoTrue(Pageable paginacao); // se seguir este padão de nomeclatura o spring consegue gerar a consulta

}
