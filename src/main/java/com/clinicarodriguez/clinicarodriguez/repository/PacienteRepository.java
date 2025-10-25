package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Buscar paciente por DNI
    Optional<Paciente> findByPaciDni(String dni);
    
    // Buscar paciente por email
    Optional<Paciente> findByPaciEmail(String email);
    
    // Buscar paciente por número de historia
    Optional<Paciente> findByPaciNumhistoria(String numHistoria);
    
    // Buscar pacientes por estado
    List<Paciente> findByPaciEstado(Integer estado);
    
    // Buscar pacientes por nombre (búsqueda parcial)
    @Query("SELECT p FROM Paciente p WHERE LOWER(p.paciNombrecompleto) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Paciente> findByNombreContaining(@Param("nombre") String nombre);
    
    // Verificar si existe DNI
    boolean existsByPaciDni(String dni);
    
    // Verificar si existe email
    boolean existsByPaciEmail(String email);
    
    // Contar pacientes activos
    @Query("SELECT COUNT(p) FROM Paciente p WHERE p.paciEstado = 1")
    long countPacientesActivos();
}
