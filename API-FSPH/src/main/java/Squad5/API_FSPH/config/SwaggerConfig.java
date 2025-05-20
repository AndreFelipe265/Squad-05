package Squad5.API_FSPH.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        // Configuração dos servidores (endereços)
        Server productionServer = new Server();
        productionServer.setUrl("https://api.fsph.com.br");
        productionServer.setDescription("Servidor de Produção");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Servidor Local de Desenvolvimento");

        // Cria e configura a documentação da API
        return new OpenAPI()
                .servers(List.of(productionServer, localServer))
                .info(
                        new Info()
                                .title("API FSPH - Gestão de Amostras")
                                .version("1.0")
                                .description("API para envio de amostras ao LACEN")
                );
    }
}
