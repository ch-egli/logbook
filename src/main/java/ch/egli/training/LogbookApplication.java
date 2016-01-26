package ch.egli.training;

import com.mangofactory.swagger.plugin.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwagger
public class LogbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogbookApplication.class, args);
	}
}
