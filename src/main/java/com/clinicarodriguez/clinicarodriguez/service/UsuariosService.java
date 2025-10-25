package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import java.util.List;
import java.util.Optional;

public interface UsuariosService {
    public List<Usuarios> findAll();

    public Usuarios findById(Long id);

    public Usuarios save(Usuarios usuario);

    public void delete(Usuarios usuario);

    public void deleteById(Long id);
    
    // Métodos para autenticación
    public Usuarios registrarUsuario(Usuarios usuario);
    
    public Optional<Usuarios> findByUsername(String username);
    
    public Optional<Usuarios> findByEmail(String email);
    
    public boolean validarCredenciales(String username, String passwordTextoPlano);
    
    public boolean existsByUsername(String username);
    
    public boolean existsByEmail(String email);
    
    public void actualizarUltimaSesion(Long usuarioId);
}

