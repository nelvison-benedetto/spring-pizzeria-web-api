package org.lessons.java.spring_pizzeriawebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SpringPizzeriaWebApiApplication {

	public static void main(String[] args) {

		// Load .env file + set env vars x spring
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));

		SpringApplication.run(SpringPizzeriaWebApiApplication.class, args);
	}

}
