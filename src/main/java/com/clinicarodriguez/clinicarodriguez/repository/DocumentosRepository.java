package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Documentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Long> {
    
    // Buscar documentos por paciente
    List<Documentos> findByPacientePaciId(Long paciId);
    
    // Buscar documentos por historia
    List<Documentos> findByHistoriaHistId(Long histId);
    
    // Buscar documentos por paciente y estado activo
    List<Documentos> findByPacientePaciIdAndDocuEstado(Long paciId, Boolean estado);
    
    // Buscar documentos visibles para paciente
    List<Documentos> findByPacientePaciIdAndDocuVisiblePacienteAndDocuEstado(Long paciId, Boolean visiblePaciente, Boolean estado);
    
    // Buscar documentos confidenciales
    List<Documentos> findByDocuConfidencialAndDocuEstado(Boolean confidencial, Boolean estado);
    
    // Buscar documentos por tipo
    List<Documentos> findByDocuTipo(String tipo);
    
    // Buscar documentos por tipo y paciente
    List<Documentos> findByDocuTipoAndPacientePaciId(String tipo, Long paciId);
    
    // Buscar documentos activos
    List<Documentos> findByDocuEstado(Boolean estado);
}
