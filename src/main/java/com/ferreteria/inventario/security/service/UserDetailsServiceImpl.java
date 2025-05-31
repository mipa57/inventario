package com.ferreteria.inventario.security.service; // Asegúrate de que el paquete sea correcto

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // En un escenario real, aquí inyectarías tu UserRepository
    // private final UserRepository userRepository;
    // public UserDetailsServiceImpl(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ***************************************************************
        // NOTA: Para este ejemplo, usamos un usuario estático.
        // En una aplicación real, buscarías el usuario en tu base de datos (MongoDB).
        // ***************************************************************
        if ("admin".equals(username)) {
            // La contraseña '1234' debe estar codificada.
            // Usamos {noop} para indicar a Spring Security que no hay codificación (solo para desarrollo).
            // En producción, usa passwordEncoder.encode("1234") y guarda el hash.
            return new User("admin", "{noop}1234", new ArrayList<>()); // {noop} indica que no hay codificación
        }
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);

        // Ejemplo de cómo sería con un UserRepository (asumiendo que tienes un modelo User y un UserRepository)
        // return userRepository.findByUsername(username)
        //         .map(user -> new User(user.getUsername(), user.getPassword(), Collections.emptyList()))
        //         .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
