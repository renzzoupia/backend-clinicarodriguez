package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Documentos;

import java.util.List;
import java.util.Optional;

public interface DocumentosService {
    
    // Métodos CRUD básicos
    List<Documentos> listarTodos();
    
    Optional<Documentos> buscarPorId(Long id);
    
    Documentos guardar(Documentos documento);
    
    Documentos actualizar(Long id, Documentos documento);
    
    void eliminar(Long id);
    
    // Métodos de búsqueda especializados
    List<Documentos> buscarPorPaciente(Long paciId);
    
    List<Documentos> buscarPorHistoria(Long histId);
    
    List<Documentos> buscarPorPacienteYEstado(Long paciId, Boolean estado);
    
    List<Documentos> buscarVisiblesParaPaciente(Long paciId, Boolean visiblePaciente, Boolean estado);
    
    List<Documentos> buscarConfidenciales(Boolean confidencial, Boolean estado);
    
    List<Documentos> buscarPorTipo(String tipo);
    
    List<Documentos> buscarPorTipoYPaciente(String tipo, Long paciId);
    
    List<Documentos> buscarPorEstado(Boolean estado);
}
