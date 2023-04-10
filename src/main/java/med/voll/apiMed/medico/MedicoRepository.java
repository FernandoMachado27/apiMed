package med.voll.apiMed.medico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long>{ // a classe e o tipo da chave primaria, substitui a classe DAO, acessa o BD

}
