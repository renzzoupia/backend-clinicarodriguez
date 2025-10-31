package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.CategoriasActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriasActivoRepository extends JpaRepository<CategoriasActivo, Integer> {
    
    Optional<CategoriasActivo> findByCaacNombreCategoria(String nombreCategoria);
    
    List<CategoriasActivo> findByCaacEstado(Integer estado);
    
    boolean existsByCaacNombreCategoria(String nombreCategoria);
}
