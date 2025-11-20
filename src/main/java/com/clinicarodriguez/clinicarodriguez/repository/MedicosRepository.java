package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicosRepository extends JpaRepository<Medicos, Integer> {
    
    // Buscar médico por persona_id
    @Query("SELECT m FROM Medicos m WHERE m.persona.persId = :personaId")
    Optional<Medicos> findByPersonaId(@Param("personaId") Integer personaId);
    
    // Buscar médico por número de colegiatura
    Optional<Medicos> findByMediNroColegiatura(String nroColegiatura);
    
    // Verificar si existe número de colegiatura
    boolean existsByMediNroColegiatura(String nroColegiatura);
    
    // Buscar médicos por estado
    @Query("SELECT m FROM Medicos m WHERE m.mediEstado = :estado")
    List<Medicos> findByEstado(@Param("estado") Boolean estado);
    
    // Listar médicos activos con información de persona
    @Query("SELECT m FROM Medicos m JOIN FETCH m.persona p WHERE m.mediEstado = true ORDER BY p.persNombrecompleto")
    List<Medicos> findAllActivosConPersona();
    
    // Listar todos los médicos con persona
    @Query("SELECT m FROM Medicos m JOIN FETCH m.persona p ORDER BY p.persNombrecompleto")
    List<Medicos> findAllConPersona();
    
    // Buscar médicos activos con especialidad específica
    @Query("SELECT DISTINCT m FROM Medicos m JOIN m.medicosEspecialidades me WHERE me.especialidad.espeId = :especialidadId AND m.mediEstado = true")
    List<Medicos> findActivosByEspecialidadId(@Param("especialidadId") Integer especialidadId);
    
    // Contar médicos activos
    @Query("SELECT COUNT(m) FROM Medicos m WHERE m.mediEstado = true")
    int countMedicosActivos();
}
