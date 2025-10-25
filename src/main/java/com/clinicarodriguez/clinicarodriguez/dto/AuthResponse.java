package com.clinicarodriguez.clinicarodriguez.dto;

import com.clinicarodriguez.clinicarodriguez.model.Usuarios;

public class AuthResponse {
    
    private String token;
    private String type = "Bearer";
    private Usuarios usuario;

    public AuthResponse() {
    }

    public AuthResponse(String token, Usuarios usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
