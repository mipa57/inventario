package com.ferreteria.inventario.security.config;

import com.ferreteria.inventario.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Importar NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    // No inyectamos AuthEntryPointJwt ni JwtFilter aquí por ahora, nos enfocamos en el login.

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usamos NoOpPasswordEncoder para contraseñas en texto plano.
        // Esto es SOLO para pruebas y NO SE DEBE USAR EN PRODUCCIÓN.
        System.out.println("DEBUG: SecurityConfig - PasswordEncoder bean creado: " + NoOpPasswordEncoder.getInstance().getClass().getName());
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // Usa el PasswordEncoder definido arriba
        System.out.println("DEBUG: SecurityConfig - DaoAuthenticationProvider configurado con UserDetailsService y PasswordEncoder.");
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita CSRF para desarrollo y APIs REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Permitir acceso a la ruta de autenticación
                        .anyRequest().authenticated() // Todas las demás rutas requieren autenticación
                )
                // Agrega el authenticationProvider al HttpSecurity para que el AuthenticationManager lo use
                .authenticationProvider(authenticationProvider()); // Importante para que el AuthenticationManager lo reconozca

        // Por ahora, no añadimos el filtro JWT si no estamos usándolo activamente.
        // .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Este método es para configurar el AuthenticationManager de forma global si fuera necesario,
    // pero con el bean AuthenticationProvider, Spring Boot suele auto-configurarlo.
    // En las versiones modernas de Spring Security, si proporcionas un AuthenticationProvider bean,
    // Spring Boot lo usa para configurar el AuthenticationManager automáticamente.
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.authenticationProvider(authenticationProvider());
    // }
}