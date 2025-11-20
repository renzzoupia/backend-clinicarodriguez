package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import java.util.List;
import java.util.Optional;

public interface UsuariosService {
    
    // CRUD básico
    List<Usuarios> findAll();
    Usuarios findById(Integer id);
    Usuarios save(Usuarios usuario);
    void delete(Usuarios usuario);
    void deleteById(Integer id);
    
    // Métodos para crear usuario completo (Persona + Usuario)
    Usuarios crearUsuarioCompleto(Personas persona, String username, String password);
    Usuarios crearUsuarioParaPersonaExistente(Personas personaExistente, String username, String password);
    Usuarios actualizarUsuarioCompleto(Integer usuarioId, Personas persona, String username, String newPassword);
    
    // Métodos para autenticación
    Usuarios registrarUsuario(Usuarios usuario);
    Optional<Usuarios> findByUsername(String username);
    Optional<Usuarios> findByPersonaId(Integer personaId);
    boolean validarCredenciales(String username, String passwordTextoPlano);
    boolean existsByUsername(String username);
    void actualizarUltimaSesion(Integer usuarioId);
    
    // Métodos por estado
    List<Usuarios> listarActivos();
    List<Usuarios> listarActivosConPersona();
    List<Usuarios> listarPorEstado(Boolean estado);
    Usuarios activar(Integer id);
    Usuarios desactivar(Integer id);
}
