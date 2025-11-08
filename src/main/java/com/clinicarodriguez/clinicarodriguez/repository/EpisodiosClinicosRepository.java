package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.EpisodiosClinicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EpisodiosClinicosRepository extends JpaRepository<EpisodiosClinicos, Integer> {

    // Buscar episodios por historia
    @Query("SELECT e FROM EpisodiosClinicos e WHERE e.historia.histId = :historiaId ORDER BY e.epclFecha DESC")
    List<EpisodiosClinicos> findByHistoriaId(@Param("historiaId") Long historiaId);

    // Buscar episodios activos por historia
    @Query("SELECT e FROM EpisodiosClinicos e WHERE e.historia.histId = :historiaId AND e.epclEstado = true ORDER BY e.epclFecha DESC")
    List<EpisodiosClinicos> findActivosByHistoriaId(@Param("historiaId") Long historiaId);

    // Buscar episodios por tipo
    @Query("SELECT e FROM EpisodiosClinicos e WHERE LOWER(e.epclTipo) = LOWER(:tipo) ORDER BY e.epclFecha DESC")
    List<EpisodiosClinicos> findByTipo(@Param("tipo") String tipo);

    // Buscar episodios por rango de fechas
    @Query("SELECT e FROM EpisodiosClinicos e WHERE e.epclFecha BETWEEN :fechaInicio AND :fechaFin ORDER BY e.epclFecha DESC")
    List<EpisodiosClinicos> findByFechaRange(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    // Buscar último episodio de una historia
    @Query("SELECT e FROM EpisodiosClinicos e WHERE e.historia.histId = :historiaId AND e.epclEstado = true ORDER BY e.epclFecha DESC LIMIT 1")
    EpisodiosClinicos findUltimoEpisodioPorHistoria(@Param("historiaId") Long historiaId);

    // Buscar episodios por estado
    @Query("SELECT e FROM EpisodiosClinicos e WHERE e.epclEstado = :estado ORDER BY e.epclFecha DESC")
    List<EpisodiosClinicos> findByEstado(@Param("estado") Boolean estado);

    // Buscar episodios por diagnóstico (búsqueda parcial)
    @Query("SELECT e FROM EpisodiosClinicos e WHERE LOWER(e.epclDiagnostico) LIKE LOWER(CONCAT('%', :diagnostico, '%')) ORDER BY e.epclFecha DESC")
    List<EpisodiosClinicos> findByDiagnosticoContaining(@Param("diagnostico") String diagnostico);
}
