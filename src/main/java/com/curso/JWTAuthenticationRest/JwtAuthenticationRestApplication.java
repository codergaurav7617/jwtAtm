package com.curso.JWTAuthenticationRest;

import com.curso.JWTAuthenticationRest.exception.NotHavingSufficentBalance;
import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import javax.persistence.EntityManagerFactory;

@SpringBootApplication(scanBasePackages= {"com.curso.JWTAuthenticationRest"})
@EnableJpaRepositories("com.curso.JWTAuthenticationRest.repositories")
@EntityScan("com.curso.JWTAuthenticationRest.model")
@EnableRetry
public class JwtAuthenticationRestApplication {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	public static void main(String[] args) throws NotHavingSufficentBalance {
		SpringApplication.run(JwtAuthenticationRestApplication.class, args);
	}
}
