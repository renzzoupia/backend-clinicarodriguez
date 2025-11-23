package com.clinicarodriguez.clinicarodriguez.config;

import com.clinicarodriguez.clinicarodriguez.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF para APIs REST
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Configurar CORS
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos (sin autenticación)
                .requestMatchers("/api/auth/**").permitAll()  // Login y registro
                .requestMatchers("/api/files/**").permitAll()  // Archivos estáticos (fotos, documentos)
                    
                // Swagger UI (documentación de API)
                .requestMatchers("/swagger-ui/**").permitAll()  // Interfaz de Swagger
                .requestMatchers("/swagger-ui.html").permitAll()  // Página principal Swagger
                .requestMatchers("/v3/api-docs/**").permitAll()  // Documentación OpenAPI JSON
                .requestMatchers("/swagger-resources/**").permitAll()  // Recursos adicionales
                .requestMatchers("/webjars/**").permitAll()  // Dependencias de Swagger
                
                // Endpoints públicos específicos del sistema
                .requestMatchers("/api/especialidades").permitAll()  // Listar especialidades
                .requestMatchers("/api/disponibilidad/especialidad/**").permitAll()  // Disponibilidad por especialidad
                .requestMatchers("/api/medicos/ver-medicos").permitAll()  // Ver médicos (solo info básica)
                .requestMatchers("/api/pacientes/registrar").permitAll()  // Registro de pacientes
                .requestMatchers("/api/usuarios/registrar").permitAll()
                .requestMatchers("/api/citas").permitAll()  // Crear citas (POST)
                .requestMatchers("/api/pacientes/dni/**").permitAll()  // Buscar paciente por DNI
                .requestMatchers("/api/documentos/paciente/dni/**").permitAll()  // Documentos por DNI
                
                // Todos los demás endpoints requieren autenticación JWT
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Sin sesiones (para JWT)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // Agregar filtro JWT

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permitir orígenes específicos
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost*",           // Desarrollo local (cualquier puerto)
            "http://127.0.0.1*",           // Desarrollo local (IP)
            "http://69.62.104.84*",        // Servidor de producción
            "https://69.62.104.84*"        // Servidor de producción con HTTPS
        ));
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
