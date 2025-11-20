package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    
    // Buscar usuario por username
    Optional<Usuarios> findByUsuaUsername(String username);
    
    // Verificar si existe un username
    boolean existsByUsuaUsername(String username);
    
    // Buscar usuario por persona_id
    @Query("SELECT u FROM Usuarios u WHERE u.persona.persId = :personaId")
    Optional<Usuarios> findByPersonaId(@Param("personaId") Integer personaId);
    
    // Listar usuarios activos
    @Query("SELECT u FROM Usuarios u WHERE u.usuaEstado = true")
    List<Usuarios> findAllActivos();
    
    // Listar usuarios con información de persona
    @Query("SELECT u FROM Usuarios u JOIN FETCH u.persona p WHERE u.usuaEstado = true ORDER BY p.persNombrecompleto")
    List<Usuarios> findAllActivosConPersona();
    
    // Buscar usuarios por estado
    @Query("SELECT u FROM Usuarios u WHERE u.usuaEstado = :estado")
    List<Usuarios> findByEstado(@Param("estado") Boolean estado);
}
