package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.RecetaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaDetalleRepository extends JpaRepository<RecetaDetalle, Integer> {
    
    // Buscar detalles por receta
    @Query("SELECT rd FROM RecetaDetalle rd WHERE rd.receta.receId = :recetaId")
    List<RecetaDetalle> findByRecetaId(@Param("recetaId") Integer recetaId);
    
    // Buscar detalles por medicamento
    @Query("SELECT rd FROM RecetaDetalle rd WHERE rd.redeMedicamento LIKE %:medicamento%")
    List<RecetaDetalle> findByMedicamento(@Param("medicamento") String medicamento);
}
