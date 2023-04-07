package med.voll.apiMed.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable // diversas Entidades que utiliza informações de endereço
@Getter
@NoArgsConstructor // gera construtor default
@AllArgsConstructor // construtor com todos campos
@Builder
public class Endereco { // JPA

	private String logradouro;
	private String bairro;
	private String cep;
	private String numero;
	private String complemento;
	private String cidade;
	private String uf;
	
	public Endereco(DadosEndereco dados) { // contrutor com DTO
		this.logradouro = dados.logradouro();
		this.bairro = dados.bairro();
		this.cep = dados.cep();
		this.numero = dados.numero();
		this.complemento = dados.complemento();
		this.cidade = dados.cidade();
		this.uf = dados.uf();
	}
}
