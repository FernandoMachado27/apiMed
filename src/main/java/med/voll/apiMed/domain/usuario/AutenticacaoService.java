package med.voll.apiMed.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // classe é um componente que executa um serviço, no caso autenticação, chamada automaticamente pelo Spring
public class AutenticacaoService implements UserDetailsService{ // implementa a interface que vai representar o serviço de autenticação, com isso o spring que chama a classe no momento da autenticação
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // roda sempe que usuario fizer login, metodo obrigatório
		return repository.findByLogin(username);
	}

}
