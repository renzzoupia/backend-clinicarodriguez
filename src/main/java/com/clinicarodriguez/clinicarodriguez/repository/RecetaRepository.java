package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {
    
    // Buscar recetas por episodio clínico
    @Query("SELECT r FROM Receta r WHERE r.episodioClinico.epclId = :episodioId")
    List<Receta> findByEpisodioId(@Param("episodioId") Integer episodioId);
    
    // Buscar recetas por estado
    List<Receta> findByReceEstado(Boolean estado);
    
    // Buscar recetas por episodio y estado
    @Query("SELECT r FROM Receta r WHERE r.episodioClinico.epclId = :episodioId AND r.receEstado = :estado")
    List<Receta> findByEpisodioIdAndEstado(@Param("episodioId") Integer episodioId, @Param("estado") Boolean estado);
}
