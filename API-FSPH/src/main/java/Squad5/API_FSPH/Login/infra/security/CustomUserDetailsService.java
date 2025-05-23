package Squad5.API_FSPH.Login.infra.security;

import Squad5.API_FSPH.Login.Repository.UsuarioRepository;
import Squad5.API_FSPH.Login.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByNomeUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return User.builder()
                .username(usuario.getNomeUsuario())
                .password(usuario.getSenha())
                .authorities(Collections.emptyList()) // ou adicionar roles se existirem
                .build();
    }
}
