package com.ferreteria.inventario.model;

public class JwtRequest {
    private String username;
    private String password;

    public JwtRequest() {}

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}


