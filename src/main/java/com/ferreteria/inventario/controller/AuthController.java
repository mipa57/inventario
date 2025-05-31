package com.ferreteria.inventario.controller; // Asegúrate de que el paquete sea correcto

import com.ferreteria.inventario.security.jwt.JwtUtil; // Asegúrate de que el paquete sea correcto
import com.ferreteria.inventario.security.service.UserDetailsServiceImpl; // Asegúrate de que el paquete sea correcto
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

// Clase para la solicitud de autenticación (usuario y contraseña)
class AuthenticationRequest {
    private String username;
    private String password;

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

// Clase para la respuesta de autenticación (el token JWT)
class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}

@RestController
@RequestMapping("/api/auth") // Prefijo para los endpoints de autenticación
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsServiceImpl userDetailsService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // Intenta autenticar al usuario usando el AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Si las credenciales son incorrectas, lanza una excepción
            throw new Exception("Credenciales incorrectas", e);
        }

        // Si la autenticación es exitosa, carga los detalles del usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Genera el token JWT
        final String jwt = jwtUtil.generateToken(userDetails);

        // Devuelve el token en la respuesta
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
