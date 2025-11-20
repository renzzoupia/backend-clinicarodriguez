package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Receta;
import java.util.List;
import java.util.Optional;

public interface RecetaService {
    
    // CRUD básico
    List<Receta> findAll();
    Optional<Receta> findById(Integer id);
    Receta save(Receta receta);
    void deleteById(Integer id);
    
    // Métodos personalizados
    List<Receta> findByEpisodioId(Integer episodioId);
    List<Receta> findByEstado(Boolean estado);
    List<Receta> findByEpisodioIdAndEstado(Integer episodioId, Boolean estado);
}
