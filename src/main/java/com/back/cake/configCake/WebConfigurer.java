package com.back.cake.configCake;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfigurer implements WebMvcConfigurer {
    

        @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir para todos os endpoints
                .allowedOrigins("http://localhost:5173") // Origem do front-end
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*")
                .allowCredentials(true); // Se usar cookies ou tokens de autenticação
    }
}
