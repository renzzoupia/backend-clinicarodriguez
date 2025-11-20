package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Roles;

import java.util.List;
import java.util.Optional;

public interface RolesService {
    
    // CRUD básico
    List<Roles> listarTodos();
    
    Optional<Roles> buscarPorId(Integer id);
    
    Optional<Roles> buscarPorNombre(String roleName);
    
    Roles guardar(Roles role);
    
    Roles actualizar(Integer id, Roles role);
    
    void eliminar(Integer id);
    
    boolean existePorNombre(String roleName);
}
