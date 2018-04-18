package eu.eoscpilot.schema2jsonld.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class Schema2JsonldApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Schema2JsonldApiApplication.class, args);
	}
}
