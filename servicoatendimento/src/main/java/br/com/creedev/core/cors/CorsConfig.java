package br.com.creedev.core.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 🌍 Origens permitidas — configure conforme o ambiente
                .allowedOrigins(
                    "http://localhost:5173",                    // ambiente local
                    "https://campospet-front.onrender.com"     // se tiver deploy no Render
                )

                // ✅ Métodos HTTP liberados
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")

                // ✅ Cabeçalhos permitidos
                .allowedHeaders("Authorization", "Content-Type", "Accept")

                // ✅ Permite envio do token JWT e cookies
                .allowCredentials(true)

                // 🕒 Cache da configuração (em segundos)
                .maxAge(3600);
    }
}