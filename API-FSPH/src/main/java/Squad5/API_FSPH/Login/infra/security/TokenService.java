package Squad5.API_FSPH.Login.infra.security;

import Squad5.API_FSPH.Login.Usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String geradorToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String Token = JWT.create()
                    .withIssuer("api-fsph-autenticacao")
                    .withSubject(usuario.getNomeUsuario())
                    .withExpiresAt(this.geradorExpiradorToken())
                    .sign(algorithm);
            return Token;


        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao tentar autenticação");
        }
    }

    public String ValidacaoToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api-fsph-autenticacao")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant geradorExpiradorToken(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3"));
    }
}
