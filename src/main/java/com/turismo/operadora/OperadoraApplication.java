package com.turismo.operadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OperadoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperadoraApplication.class, args);
	}

	// Configuración global de CORS
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Aplica esta configuración a todos los endpoints de tu API
						.allowedOrigins("http://localhost", "http://127.0.0.1") // <-- ¡Asegúrate de incluir "http://localhost"!
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
						.allowedHeaders("*") // Cabeceras HTTP permitidas
						.allowCredentials(true); // Permite enviar cookies o cabeceras de autenticación (si las usas)
			}
		};
	}
}
