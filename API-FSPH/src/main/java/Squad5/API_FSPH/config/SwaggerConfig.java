package Squad5.API_FSPH.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        // cria e configura a doc da API que vai aparecer no Swagger
        return new OpenAPI().info(
                new Info()
                        .title("API FSPH - Gestão de Amostras") // nome que aparece como título
                        .version("1.0") // versão atual da API
                        .description("API para envio de amostras ao LACEN") // explicação rápida do propósito
        );
    }
}
