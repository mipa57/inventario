package com.ferreteria.inventario.model;

public class JwtResponse {
    private String token;

    public JwtResponse() {}

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
}


