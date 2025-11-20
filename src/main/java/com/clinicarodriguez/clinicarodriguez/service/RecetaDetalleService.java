package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.RecetaDetalle;
import java.util.List;
import java.util.Optional;

public interface RecetaDetalleService {
    
    // CRUD básico
    List<RecetaDetalle> findAll();
    Optional<RecetaDetalle> findById(Integer id);
    RecetaDetalle save(RecetaDetalle recetaDetalle);
    void deleteById(Integer id);
    
    // Métodos personalizados
    List<RecetaDetalle> findByRecetaId(Integer recetaId);
    List<RecetaDetalle> findByMedicamento(String medicamento);
}
