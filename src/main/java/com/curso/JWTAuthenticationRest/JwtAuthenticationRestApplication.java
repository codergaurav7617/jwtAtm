package com.curso.JWTAuthenticationRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication(scanBasePackages= {"com.curso.JWTAuthenticationRest"})
@EnableJpaRepositories("com.curso.JWTAuthenticationRest.repositories")
public class JwtAuthenticationRestApplication {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationRestApplication.class, args);
	}

}
