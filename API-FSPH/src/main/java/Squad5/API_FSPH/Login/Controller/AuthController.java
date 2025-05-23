package Squad5.API_FSPH.Login.Controller;


import Squad5.API_FSPH.Login.Controller.DTO.LoginRequestDTO;
import Squad5.API_FSPH.Login.Controller.DTO.RegisterRequestDTO;
import Squad5.API_FSPH.Login.Controller.DTO.ResponseDTO;
import Squad5.API_FSPH.Login.Repository.UsuarioRepository;
import Squad5.API_FSPH.Login.Usuario.Usuario;
import Squad5.API_FSPH.Login.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        Usuario usuario = this.repository.findByNomeUsuario(body.nomeUsuario()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if(passwordEncoder.matches(body.senha(), usuario.getSenha())) {
            String token = this.tokenService.geradorToken(usuario);
            return ResponseEntity.ok(new ResponseDTO(usuario.getNomeUsuario(), token));

        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> usuario = this.repository.findByNomeUsuario(body.nomeUsuario());
        if (usuario.isEmpty()) {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setSenha(passwordEncoder.encode(body.senha()));
            novoUsuario.setNomeUsuario(body.nomeUsuario());
            novoUsuario.setUserId(body.userId());
            this.repository.save(novoUsuario);
            String token = this.tokenService.geradorToken(novoUsuario);
            return ResponseEntity.ok(new ResponseDTO(novoUsuario.getNomeUsuario(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
