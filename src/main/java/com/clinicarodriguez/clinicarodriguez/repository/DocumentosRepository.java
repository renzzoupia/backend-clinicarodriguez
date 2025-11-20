package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Documentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Integer> {
    
    // Buscar documentos por historia
    List<Documentos> findByHistoriaHistId(Integer histId);
    
    // Buscar documentos confidenciales
    List<Documentos> findByDocuConfidencialAndDocuEstado(Boolean confidencial, Boolean estado);
    
    // Buscar documentos por tipo
    List<Documentos> findByDocuTipo(String tipo);
    
    // Buscar documentos activos
    List<Documentos> findByDocuEstado(Boolean estado);
    
    // Buscar documentos visibles para paciente por DNI (excluye confidenciales)
    @Query("SELECT d FROM Documentos d " +
           "JOIN d.historia h " +
           "JOIN h.paciente p " +
           "JOIN p.persona per " +
           "WHERE per.persNroDoc = :dni " +
           "AND d.docuVisiblePaciente = true " +
           "AND d.docuConfidencial = false " +
           "AND d.docuEstado = true " +
           "ORDER BY d.docuFechaSubida DESC")
    List<Documentos> findByPacienteDniVisibles(@Param("dni") String dni);
}
