package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Historias;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HistoriasRepository extends JpaRepository<Historias, Integer> {
    
    // Buscar historias por paciente
    @Query("SELECT h FROM Historias h WHERE h.paciente.paciId = :pacienteId ORDER BY h.histRegistrofecha DESC")
    List<Historias> findByPacienteId(@Param("pacienteId") Integer pacienteId);
    
    // Buscar historias por usuario (médico)
    @Query("SELECT h FROM Historias h WHERE h.usuario.usuaId = :usuarioId ORDER BY h.histRegistrofecha DESC")
    List<Historias> findByUsuarioId(@Param("usuarioId") Integer usuarioId);
    
    
    // Buscar historias por rango de fechas
    //@Query("SELECT h FROM Historias h WHERE h.histFecha BETWEEN :fechaInicio AND :fechaFin ORDER BY h.histFecha DESC")
    //List<Historias> findByFechaRange(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    // Contar historias de un paciente
    @Query("SELECT COUNT(h) FROM Historias h WHERE h.paciente.paciId = :pacienteId")
    long countByPacienteId(@Param("pacienteId") Integer pacienteId);
}
