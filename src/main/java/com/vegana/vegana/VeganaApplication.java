package com.vegana.vegana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@EntityScan(basePackages = {VeganaApplication.BASE_PACKAGE_CORE})
@SpringBootApplication(scanBasePackages = {VeganaApplication.BASE_PACKAGE_CORE})
public class VeganaApplication {

	public static final String BASE_PACKAGE_CORE = "com.company.vegana";

	public static void main(String[] args) {
		SpringApplication.run(VeganaApplication.class, args);
	}

}
