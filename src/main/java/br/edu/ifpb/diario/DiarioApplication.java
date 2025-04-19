package br.edu.ifpb.diario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

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
