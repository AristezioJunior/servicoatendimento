package br.com.creedev.core.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração de Cross-Origin Resource Sharing (CORS) para permitir
 * que aplicações frontend de domínios diferentes acessem a API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Aplica a configuração CORS a todos os endpoints da API (/**)
        registry.addMapping("/**")
                
                // Configuração ideal para AMBIENTE DE DESENVOLVIMENTO:
                // Permite acesso de qualquer origem.
                .allowedOrigins("*") 
                
                /*
                 * Configuração ideal para PRODUÇÃO (substitua pelo domínio real do seu frontend):
                 * .allowedOrigins("https://seufrotend.com.br", "http://localhost:3000")
                 */

                // Define quais métodos HTTP são permitidos (CRUD completo)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                
                // Permite que a requisição envie quaisquer headers (incluindo 'Authorization' para JWT)
                .allowedHeaders("*")
                
                // Define por quanto tempo (em segundos) o navegador pode cachear os resultados da checagem CORS.
                .maxAge(3600);
    }
}