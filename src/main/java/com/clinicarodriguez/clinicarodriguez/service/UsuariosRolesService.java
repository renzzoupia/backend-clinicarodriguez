package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.UsuariosRoles;

import java.util.List;
import java.util.Optional;

public interface UsuariosRolesService {
    
    // Listar todos
    List<UsuariosRoles> listarTodos();
    
    // Buscar por ID
    Optional<UsuariosRoles> buscarPorId(Long id);
    
    // Obtener todos los roles de un usuario
    List<UsuariosRoles> obtenerRolesDeUsuario(Long usuarioId);
    
    // Obtener todos los usuarios con un rol específico
    List<UsuariosRoles> obtenerUsuariosConRol(Long roleId);
    
    // Asignar un rol a un usuario
    UsuariosRoles asignarRol(Long usuarioId, Long roleId);
    
    // Quitar un rol de un usuario
    void quitarRol(Long usuarioId, Long roleId);
    
    // Quitar todos los roles de un usuario
    void quitarTodosLosRolesDeUsuario(Long usuarioId);
    
    // Verificar si un usuario tiene un rol específico
    boolean usuarioTieneRol(Long usuarioId, Long roleId);
}
