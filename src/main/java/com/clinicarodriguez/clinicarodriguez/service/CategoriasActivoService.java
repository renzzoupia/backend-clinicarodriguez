package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.CategoriasActivo;

import java.util.List;
import java.util.Optional;

public interface CategoriasActivoService {
    
    List<CategoriasActivo> findAll();
    
    Optional<CategoriasActivo> findById(Integer id);
    
    CategoriasActivo save(CategoriasActivo categoriasActivo);
    
    CategoriasActivo update(Integer id, CategoriasActivo categoriasActivo);
    
    void deleteById(Integer id);
    
    Optional<CategoriasActivo> findByNombreCategoria(String nombreCategoria);
    
    List<CategoriasActivo> findByEstado(Integer estado);
    
    boolean existsByNombreCategoria(String nombreCategoria);
}
