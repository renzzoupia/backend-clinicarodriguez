package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Documentos;

import java.util.List;
import java.util.Optional;

public interface DocumentosService {
    
    // Métodos CRUD básicos
    List<Documentos> listarTodos();
    
    Optional<Documentos> buscarPorId(Integer id);
    
    Documentos guardar(Documentos documento);
    
    Documentos actualizar(Integer id, Documentos documento);
    
    void eliminar(Integer id);
    
    // Métodos de búsqueda especializados
    List<Documentos> buscarPorHistoria(Integer histId);
    
    List<Documentos> buscarConfidenciales(Boolean confidencial, Boolean estado);
    
    List<Documentos> buscarPorTipo(String tipo);
    
    List<Documentos> buscarPorEstado(Boolean estado);
    
    // Buscar documentos visibles para paciente por DNI (excluye confidenciales)
    List<Documentos> buscarPorPacienteDni(String dni);
}
