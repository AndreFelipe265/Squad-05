package Squad5.API_FSPH;

import Squad5.API_FSPH.repository.AmostraRepository;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ApiFsphApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFsphApplication.class, args);
	}
}
