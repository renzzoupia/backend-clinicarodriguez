package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    
    // Buscar usuario por username
    Optional<Usuarios> findByUsuaUsername(String username);
    
    // Buscar usuario por email
    Optional<Usuarios> findByUsuaEmail(String email);
    
    // Verificar si existe un username
    boolean existsByUsuaUsername(String username);
    
    // Verificar si existe un email
    boolean existsByUsuaEmail(String email);
}

