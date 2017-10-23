package ch.egli.training;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;

/**
 * Main application class.
 *
 * @author Christian Egli
 * @since 1/31/16.
 */
@SpringBootApplication
@EnableSwagger2
public class LogbookApplication {

	public static String ROOT = "upload-dir";

	public static void main(String[] args) {
		SpringApplication.run(LogbookApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return (String[] args) -> {
			new File(ROOT).mkdir();
		};
	}

}
