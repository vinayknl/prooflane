package nl.vinaykumar.prooflane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProoflaneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProoflaneApplication.class, args);
	}

}
