package med.voll.apiMed.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long>{ // a classe e o tipo da chave primaria, substitui a classe DAO, acessa o BD

	Page<Medico> findAllByAtivoTrue(Pageable paginacao); // se seguir este padão de nomeclatura o spring consegue gerar a consulta

	@Query("""
			select m from Medico m
			where
			m.ativo = 1
			and 
			m.especialidade = :especialidade
			and
			m.id not in(
				select c.medico from Consulta c
				where
				c.data = :date
			)
			order by rand()
			limit 1
			""") // Traz todos os medicos ativos que sejam dessa especialidade e me retorna 1 aleatório com a data vazia
	Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data); // nós que fazemos a consulta

}
