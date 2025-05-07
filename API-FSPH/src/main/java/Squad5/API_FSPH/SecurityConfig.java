package Squad5.API_FSPH;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilitar CSRF enquanto você não usa token ou login por formulário
                .authorizeHttpRequests(auth -> auth
                        // Liberando as rotas públicas (incluindo a base /v1/Amostra)
                        .requestMatchers("/v1/Amostra/**").permitAll()  // Libera acesso a todas as rotas dentro de /v1/Amostra
                        .anyRequest().authenticated()  // Requer autenticação para outras rotas
                )
                .httpBasic(Customizer.withDefaults()) // Autenticação básica (pode ser substituída por JWT no futuro)
                .build();
    }
}
