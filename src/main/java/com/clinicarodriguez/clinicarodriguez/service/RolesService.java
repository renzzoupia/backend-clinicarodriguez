package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Roles;

import java.util.List;
import java.util.Optional;

public interface RolesService {
    
    // CRUD básico
    List<Roles> listarTodos();
    
    Optional<Roles> buscarPorId(Long id);
    
    Optional<Roles> buscarPorNombre(String roleName);
    
    Roles guardar(Roles role);
    
    Roles actualizar(Long id, Roles role);
    
    void eliminar(Long id);
    
    boolean existePorNombre(String roleName);
}
