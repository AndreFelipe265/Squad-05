package Squad5.API_FSPH.Login.Repository;

import Squad5.API_FSPH.Login.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
}
