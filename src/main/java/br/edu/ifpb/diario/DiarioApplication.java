package br.edu.ifpb.diario;

import io.github.cdimascio.dotenv.Dotenv;
import me.paulschwarz.springdotenv.DotenvConfig;
import me.paulschwarz.springdotenv.DotenvPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

@SpringBootApplication
public class DiarioApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DiarioApplication.class);

		app.addInitializers(applicationContext -> {
			Dotenv dotenv = Dotenv.load();
		});

		app.run(args);
	}

}
