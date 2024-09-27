package com.indus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class IndusTechnologyApplication implements CommandLineRunner {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(IndusTechnologyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(
				"=============================Starting TRAINING-SERVICE===========================================");
		System.out.println("CLIENT_PORT Value : " + environment.getProperty("CLIENT_PORT"));
		System.out.println("EUREKA_DEFAULT_ZONE Value : " + environment.getProperty("EUREKA_DEFAULT_ZONE"));
		System.out.println("DATASOURCE_URL Value : " + environment.getProperty("DATASOURCE_URL"));
		System.out.println("DATASOURCE_USERNAME Value : " + environment.getProperty("DATASOURCE_USERNAME"));
		System.out.println("DATASOURCE_PASSWORD Value : " + environment.getProperty("DATASOURCE_PASSWORD"));
		System.out.println(
				"DATASOURCE_HIKARI_AUTO_COMMIT Value : " + environment.getProperty("DATASOURCE_HIKARI_AUTO_COMMIT"));
		System.out.println("DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE Value : "
				+ environment.getProperty("DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE"));
		System.out.println("DATASOURCE_HIKARI_MINIMIUM_IDLE Value : "
				+ environment.getProperty("DATASOURCE_HIKARI_MINIMIUM_IDLE"));
		System.out.println(
				"DATASOURCE_HIKARI_IDLE_TIMEOUT Value : " + environment.getProperty("DATASOURCE_HIKARI_IDLE_TIMEOUT"));
		System.out
				.println("DATASOURCE_CONTINUEONERROR Value : " + environment.getProperty("DATASOURCE_CONTINUEONERROR"));
		System.out.println("JPA_GENERATE_DDL Value : " + environment.getProperty("JPA_GENERATE_DDL"));
		System.out.println("JPA_SHOW_SQL Value : " + environment.getProperty("JPA_SHOW_SQL"));
		System.out.println("JPA_HIBERNATE_DDL_AUTO Value : " + environment.getProperty("JPA_HIBERNATE_DDL_AUTO"));
		System.out.println("HIBERNATE_TEMP_USE_JDBC_METADATA_DEFAULTS Value : "
				+ environment.getProperty("HIBERNATE_TEMP_USE_JDBC_METADATA_DEFAULTS"));
		System.out.println("MULTIPART_ENABLED Value : " + environment.getProperty("MULTIPART_ENABLED"));
		System.out.println(
				"MULTIPART_FILE_SIZE_THRESHOLD Value : " + environment.getProperty("MULTIPART_FILE_SIZE_THRESHOLD"));
		System.out.println("MULTIPART_MAX_FILE_SIZE Value : " + environment.getProperty("MULTIPART_MAX_FILE_SIZE"));

	}

}
