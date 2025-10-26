package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    
    // Buscar rol por nombre
    Optional<Roles> findByRoleName(String roleName);
    
    // Verificar si existe un rol por nombre
    boolean existsByRoleName(String roleName);
}
