package com.ferreteria.inventario.config; // Asegúrate de que el paquete sea correcto

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todos los endpoints en tu API
                .allowedOrigins("http://127.0.0.1:5500") // Permite peticiones desde tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos los encabezados (incluido Authorization)
                .allowCredentials(true); // Permite el envío de credenciales (cookies, encabezados de autorización)
    }
}

