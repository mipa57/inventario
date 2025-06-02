package com.ferreteria.inventario.security.service; // Asegúrate de que el paquete sea correcto

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("DEBUG: UserDetailsServiceImpl - loadUserByUsername llamado con: '" + username + "'");

        if ("admin".equals(username)) {
            System.out.println("DEBUG: UserDetailsServiceImpl - Usuario 'admin' encontrado en el servicio.");
            // La contraseña DEBE tener el prefijo "{noop}" para que NoOpPasswordEncoder la reconozca.
            return new User("admin", "{noop}1234", new ArrayList<>());
        } else {
            System.out.println("DEBUG: UserDetailsServiceImpl - Usuario '" + username + "' NO encontrado.");
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }
}
