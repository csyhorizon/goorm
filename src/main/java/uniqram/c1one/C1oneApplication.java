package uniqram.c1one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class C1oneApplication {

	public static void main(String[] args) {
		SpringApplication.run(C1oneApplication.class, args);
	}

}
