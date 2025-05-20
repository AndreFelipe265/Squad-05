package Squad5.API_FSPH.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(
                new Info()
                        .title("API FSPH - Gestão de Amostras")
                        .version("1.0")
                        .description("API para envio de amostras ao LACEN")
        );
    }
}
