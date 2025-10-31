package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.ActivosTecnologicos;
import com.clinicarodriguez.clinicarodriguez.model.CategoriasActivo;
import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivosTecnologicosRepository extends JpaRepository<ActivosTecnologicos, Integer> {
    
    Optional<ActivosTecnologicos> findByActeCodigoActivo(String codigoActivo);
    
    List<ActivosTecnologicos> findByCategoria(CategoriasActivo categoria);
    
    List<ActivosTecnologicos> findByActeEstado(String estado);
    
    List<ActivosTecnologicos> findByActeUbicacion(String ubicacion);
    
    List<ActivosTecnologicos> findByUsuario(Usuarios usuario);
    
    boolean existsByActeCodigoActivo(String codigoActivo);
    
    boolean existsByActeNumeroSerie(String numeroSerie);
}
