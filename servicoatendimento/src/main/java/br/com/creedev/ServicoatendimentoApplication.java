package br.com.creedev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.creedev.core.security.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class ServicoatendimentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicoatendimentoApplication.class, args);
	}

}
