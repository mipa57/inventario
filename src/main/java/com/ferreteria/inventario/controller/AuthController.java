package com.ferreteria.inventario.controller; // Asegúrate de que el paquete sea correcto

import org.springframework.http.ResponseEntity; // Asegúrate de que el paquete sea correcto
import org.springframework.security.authentication.AuthenticationManager; // Asegúrate de que el paquete sea correcto
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.inventario.security.jwt.JwtUtil;
import com.ferreteria.inventario.security.service.UserDetailsServiceImpl;

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
        // --- NUEVAS LÍNEAS DE DEPURACIÓN AQUÍ ---
        System.out.println("DEBUG: AuthController - Petición de login recibida.");
        System.out.println("DEBUG: AuthController - Usuario recibido: '" + authenticationRequest.getUsername() + "'");
        // Por seguridad, no imprimas la contraseña completa en un log de producción.
        // Aquí imprimimos solo los primeros caracteres para depuración.
        String password = authenticationRequest.getPassword();
        System.out.println("DEBUG: AuthController - Contraseña recibida (parcial): '" + password.substring(0, Math.min(password.length(), 2)) + "...'");
        // ------------------------------------------

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            // --- NUEVA LÍNEA DEBUG: Si llega aquí, es exitoso (teóricamente) ---
            System.out.println("DEBUG: AuthController - authenticationManager.authenticate() EXITOSO.");
            // ------------------------------------------------------------------
        } catch (BadCredentialsException e) {
            // --- NUEVA LÍNEA DEBUG AQUÍ ---
            System.out.println("DEBUG: AuthController - Capturada BadCredentialsException: " + e.getMessage());
            // --------------------------------
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
