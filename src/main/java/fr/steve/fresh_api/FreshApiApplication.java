package fr.steve.fresh_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FreshApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreshApiApplication.class, args);
	}

}
