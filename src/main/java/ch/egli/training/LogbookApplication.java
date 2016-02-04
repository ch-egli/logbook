package ch.egli.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main application class.
 *
 * @author Christian Egli
 * @since 1/31/16.
 */
@SpringBootApplication
@EnableSwagger2
public class LogbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogbookApplication.class, args);
	}
}
