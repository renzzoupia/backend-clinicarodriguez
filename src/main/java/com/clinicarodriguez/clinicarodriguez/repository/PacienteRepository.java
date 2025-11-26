package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    
    // Buscar paciente por persona_id
    @Query("SELECT p FROM Paciente p WHERE p.persona.persId = :personaId")
    Optional<Paciente> findByPersonaId(@Param("personaId") Integer personaId);
    
    // Buscar pacientes por estado
    @Query("SELECT p FROM Paciente p WHERE p.paciEstado = :estado")
    List<Paciente> findByEstado(@Param("estado") Integer estado);
    
    // Listar pacientes activos con información de persona
    @Query("SELECT p FROM Paciente p JOIN FETCH p.persona per WHERE p.paciEstado = true ORDER BY per.persNombrecompleto")
    List<Paciente> findAllActivosConPersona();
    
    // Listar todos los pacientes con persona
    @Query("SELECT p FROM Paciente p JOIN FETCH p.persona per ORDER BY per.persNombrecompleto")
    List<Paciente> findAllConPersona();
    
    
    // Contar pacientes activos
    @Query("SELECT COUNT(p) FROM Paciente p WHERE p.paciEstado = true")
    long countPacientesActivos();
    
    // Buscar pacientes por DNI parcial (para autocompletado)
    @Query("SELECT p FROM Paciente p JOIN FETCH p.persona per " +
           "WHERE per.persNroDoc LIKE CONCAT(:dniBusqueda, '%') " +
           "AND p.paciEstado = true " +
           "ORDER BY per.persNroDoc")
    List<Paciente> findByDniStartingWith(@Param("dniBusqueda") String dniBusqueda);
    
    // Buscar paciente por DNI exacto
    @Query("SELECT p FROM Paciente p JOIN FETCH p.persona per " +
           "WHERE per.persNroDoc = :dni")
    Optional<Paciente> findByDniExacto(@Param("dni") String dni);
    
    // Buscar pacientes por DNI parcial SIN historia clínica (para autocompletado)
    @Query("SELECT p FROM Paciente p JOIN FETCH p.persona per " +
           "WHERE per.persNroDoc LIKE CONCAT(:dniBusqueda, '%') " +
           "AND p.paciEstado = true " +
           "AND NOT EXISTS (SELECT h FROM Historias h WHERE h.paciente.paciId = p.paciId) " +
           "ORDER BY per.persNroDoc")
    List<Paciente> findByDniStartingWithSinHistoria(@Param("dniBusqueda") String dniBusqueda);
}
