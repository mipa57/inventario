package com.ferreteria.inventario.security.config;

import com.ferreteria.inventario.security.jwt.JwtRequestFilter;
import com.ferreteria.inventario.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Nuevo import
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Solo para desarrollo, NO USAR EN PRODUCCIÓN
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; // Nuevo import
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring
@EnableMethodSecurity(prePostEnabled = true) // Reemplazo de @EnableGlobalMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Define el PasswordEncoder.
    // **************************************************************************
    // ADVERTENCIA: NoOpPasswordEncoder.getInstance() es solo para DESARROLLO.
    // En producción, DEBES usar un codificador de contraseñas seguro como BCryptPasswordEncoder.
    // Por ejemplo: return new BCryptPasswordEncoder();
    // **************************************************************************
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // No codifica la contraseña (solo para pruebas con {noop}1234)
        // return new BCryptPasswordEncoder(); // Opción segura para producción
    }

    // Configura el AuthenticationManager como un Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Define el DaoAuthenticationProvider para configurar UserDetailsService y PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Configura las reglas de autorización HTTP y los filtros
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (nueva sintaxis para Spring Security 6)
                .authorizeHttpRequests(authorize -> authorize // Nueva sintaxis para reglas de autorización
                        .requestMatchers("/api/auth/login").permitAll() // Permite el acceso a la URL de login sin autenticación
                        .requestMatchers("/api/productos/**").authenticated() // Protege todos los endpoints de productos
                        .anyRequest().authenticated() // Cualquier otra petición requiere autenticación
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Las sesiones son sin estado (para JWT)
                );

        // Agrega el filtro JWT antes del filtro de autenticación de usuario/contraseña
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Construye y devuelve el SecurityFilterChain
    }
}