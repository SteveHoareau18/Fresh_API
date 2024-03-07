package fr.steve.fresh_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySource(value = {"classpath:application-local.properties"},
                ignoreResourceNotFound = true)
public class FreshApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreshApiApplication.class, args);
	}

}
