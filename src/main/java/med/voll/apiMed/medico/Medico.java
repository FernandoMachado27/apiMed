package med.voll.apiMed.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.apiMed.endereco.Endereco;

@Table(name = "medicos") // nome da tabela
@Entity(name = "Medico") // entidade
@Getter
@NoArgsConstructor // gera construtor default
@AllArgsConstructor // construtor com todos campos
@EqualsAndHashCode(of = "id") // equals e hashCode em cima do ID
@Builder
public class Medico { // JPA
	
	public Medico(DadosCadastroMedico dados) { // contrutor com DTO
		this.nome = dados.nome();
		this.email = dados.email();
		this.crm = dados.crm();
		this.especialidade = dados.especialidade();
		this.endereco = new Endereco(dados.endereco());
	}

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String crm;
	
	@Enumerated(EnumType.STRING) // converter enum em String
	private Especialidade especialidade;
	
	@Embedded // classe separadas mas salvos juntos na mesma tabela
	private Endereco endereco;
}
