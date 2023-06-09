package med.voll.apiMed.domain.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.apiMed.domain.endereco.Endereco;

@Table(name = "medicos") // nome da tabela
@Entity(name = "Medico") // entidade
@Getter
@NoArgsConstructor // gera construtor default
@AllArgsConstructor // construtor com todos campos
@EqualsAndHashCode(of = "id") // equals e hashCode em cima do ID
@Builder
public class Medico { // JPA, entidade
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String crm;
	@Enumerated(EnumType.STRING) // converter enum em String
	private Especialidade especialidade;
	@Embedded // classe separadas mas salvos juntos na mesma tabela
	private Endereco endereco;
	private boolean ativo;
	
	public Medico(DadosCadastroMedico dados) { // contrutor com DTO
		this.ativo = true;
		this.nome = dados.nome();
		this.email = dados.email();
		this.telefone = dados.telefone();
		this.crm = dados.crm();
		this.especialidade = dados.especialidade();
		this.endereco = new Endereco(dados.endereco());
	}

	public void atualizarInformacoes(@Valid DadosAtualizaçaoMedico dados) {
		if(dados.nome() != null) { // atualiza se for diferente de nulo
			this.nome = dados.nome();
		}
		
		if (dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		
		if (dados.endereco() != null) {
			this.endereco.atualizarInformacoes(dados.endereco());
		}
	}

	public void excluir() {
		this.ativo = false;
	}
}
