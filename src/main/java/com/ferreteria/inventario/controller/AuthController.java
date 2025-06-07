package com.ferreteria.inventario.controller;

import com.ferreteria.inventario.model.Usuario;
import com.ferreteria.inventario.model.JwtRequest;
import com.ferreteria.inventario.model.JwtResponse;
import com.ferreteria.inventario.repository.UsuarioRepository;
import com.ferreteria.inventario.security.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario) {
        usuarioRepository.save(usuario);
        return "✅ Usuario registrado correctamente";
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest authRequest) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return new JwtResponse(token);
    }
}

