package com.curso.JWTAuthenticationRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication(scanBasePackages= {"com.curso.JWTAuthenticationRest"})
@EnableJpaRepositories("com.curso.JWTAuthenticationRest.repositories")
@EnableRetry
public class JwtAuthenticationRestApplication {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationRestApplication.class, args);
	}
}
