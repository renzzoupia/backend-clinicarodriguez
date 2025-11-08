package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Triaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TriajeRepository extends JpaRepository<Triaje, Long> {

    // Buscar triajes por historia
    @Query("SELECT t FROM Triaje t WHERE t.historia.histId = :historiaId ORDER BY t.triaFecha DESC")
    List<Triaje> findByHistoriaId(@Param("historiaId") Long historiaId);

    // Buscar triajes activos por historia
    @Query("SELECT t FROM Triaje t WHERE t.historia.histId = :historiaId AND t.triaEstado = true ORDER BY t.triaFecha DESC")
    List<Triaje> findActivosByHistoriaId(@Param("historiaId") Long historiaId);

    // Buscar triajes por rango de fechas
    @Query("SELECT t FROM Triaje t WHERE t.triaFecha BETWEEN :fechaInicio AND :fechaFin ORDER BY t.triaFecha DESC")
    List<Triaje> findByFechaRange(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    // Buscar último triaje de una historia
    @Query("SELECT t FROM Triaje t WHERE t.historia.histId = :historiaId AND t.triaEstado = true ORDER BY t.triaFecha DESC LIMIT 1")
    Triaje findUltimoTriajePorHistoria(@Param("historiaId") Long historiaId);

    // Buscar triajes por estado
    @Query("SELECT t FROM Triaje t WHERE t.triaEstado = :estado ORDER BY t.triaFecha DESC")
    List<Triaje> findByEstado(@Param("estado") Boolean estado);
}
