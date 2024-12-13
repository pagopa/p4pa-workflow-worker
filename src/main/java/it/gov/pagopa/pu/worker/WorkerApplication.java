package it.gov.pagopa.pu.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "it.gov.pagopa.payhub.activities")
public class WorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkerApplication.class, args);
	}

}
