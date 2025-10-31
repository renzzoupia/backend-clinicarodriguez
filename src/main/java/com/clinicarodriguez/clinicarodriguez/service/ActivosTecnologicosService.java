package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.ActivosTecnologicos;

import java.util.List;
import java.util.Optional;

public interface ActivosTecnologicosService {
    
    List<ActivosTecnologicos> findAll();
    
    Optional<ActivosTecnologicos> findById(Integer id);
    
    ActivosTecnologicos save(ActivosTecnologicos activosTecnologicos);
    
    ActivosTecnologicos update(Integer id, ActivosTecnologicos activosTecnologicos);
    
    void deleteById(Integer id);
    
    Optional<ActivosTecnologicos> findByCodigoActivo(String codigoActivo);
    
    List<ActivosTecnologicos> findByCategoria(Integer categoriaId);
    
    List<ActivosTecnologicos> findByEstado(String estado);
    
    List<ActivosTecnologicos> findByUbicacion(String ubicacion);
    
    List<ActivosTecnologicos> findByUsuarioId(Integer usuaId);
    
    boolean existsByCodigoActivo(String codigoActivo);
    
    boolean existsByNumeroSerie(String numeroSerie);
}
