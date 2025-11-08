package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Areas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreasRepository extends JpaRepository<Areas, Integer> {

    // Buscar áreas raíz (sin padre)
    @Query("SELECT a FROM Areas a WHERE a.areaPadre IS NULL")
    List<Areas> findAreasRaiz();

    // Buscar subáreas de un área padre específica
    @Query("SELECT a FROM Areas a WHERE a.areaPadre.areaId = :areaPadreId")
    List<Areas> findSubAreasByPadreId(@Param("areaPadreId") Integer areaPadreId);

    // Buscar áreas por nombre (búsqueda parcial)
    @Query("SELECT a FROM Areas a WHERE LOWER(a.areaNombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Areas> findByNombreContaining(@Param("nombre") String nombre);

    // Verificar si un área tiene subáreas
    @Query("SELECT COUNT(a) > 0 FROM Areas a WHERE a.areaPadre.areaId = :areaId")
    boolean tieneSubAreas(@Param("areaId") Integer areaId);
}
