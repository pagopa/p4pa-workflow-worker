package it.gov.pagopa.pu.worker;

import it.gov.pagopa.payhub.activities.util.Utilities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.webmvc.autoconfigure.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan(basePackages = {"it.gov.pagopa.pu.worker", "it.gov.pagopa.payhub.activities"})
public class WorkerApplication {

	public static void main(String[] args) {
    TimeZone.setDefault(Utilities.DEFAULT_TIMEZONE);
		SpringApplication.run(WorkerApplication.class, args);
	}

}
