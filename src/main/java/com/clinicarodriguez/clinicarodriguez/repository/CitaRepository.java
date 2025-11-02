package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Cita;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    // Buscar citas por paciente
    @Query("SELECT c FROM Cita c WHERE c.paciente.paciId = :pacienteId")
    List<Cita> findByPacienteId(@Param("pacienteId") Long pacienteId);
    
    // Buscar citas por médico
    @Query("SELECT c FROM Cita c WHERE c.medico.mediId = :medicoId")
    List<Cita> findByMedicoId(@Param("medicoId") Long medicoId);
    
    // Buscar citas por fecha
    List<Cita> findByCitaFecha(LocalDate fecha);
    
    // Buscar citas por médico y fecha
    @Query("SELECT c FROM Cita c WHERE c.medico.mediId = :medicoId AND c.citaFecha = :fecha")
    List<Cita> findByMedicoIdAndFecha(@Param("medicoId") Long medicoId, @Param("fecha") LocalDate fecha);
    
    // Buscar citas por estado
    List<Cita> findByCitaEstado(String estado);
    
    // Buscar citas por rango de fechas
    @Query("SELECT c FROM Cita c WHERE c.citaFecha BETWEEN :fechaInicio AND :fechaFin")
    List<Cita> findByFechaRange(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    // Contar citas por médico y fecha
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.medico.mediId = :medicoId AND c.citaFecha = :fecha")
    long countByMedicoAndFecha(@Param("medicoId") Long medicoId, @Param("fecha") LocalDate fecha);
    
    // Buscar próximas citas de un paciente
    @Query("SELECT c FROM Cita c WHERE c.paciente.paciId = :pacienteId AND c.citaFecha >= :fecha ORDER BY c.citaFecha ASC")
    List<Cita> findProximasCitasByPaciente(@Param("pacienteId") Long pacienteId, @Param("fecha") LocalDate fecha);
}
