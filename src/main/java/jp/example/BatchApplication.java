package jp.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Main entry point for the BatchApplication.
 * This class starts the Spring Boot application and runs the CSV batch processing job.
 */
@SpringBootApplication
public class BatchApplication {
	
	/**
	 * Main method to start the Spring Boot application.
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
	
	/**
	 * Runs the CSV batch job when the application starts.
	 * @param ctx The application context to access Spring beans.
	 * @return CommandLineRunner
	 */
	@Bean
	public CommandLineRunner run(ApplicationContext ctx) {
		return args -> {
			CsvBatchJob csvBatchJob = ctx.getBean(CsvBatchJob.class);
			csvBatchJob.processCsvFiles();
		};
	}

}
