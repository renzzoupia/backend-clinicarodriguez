package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Triaje;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TriajeService {

    // CRUD básico
    List<Triaje> listarTodos();
    Optional<Triaje> buscarPorId(Long id);
    Triaje guardar(Triaje triaje);
    void eliminar(Long id);

    // Métodos específicos
    List<Triaje> listarPorHistoria(Long historiaId);
    List<Triaje> listarActivosPorHistoria(Long historiaId);
    List<Triaje> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);
    Triaje obtenerUltimoTriajePorHistoria(Long historiaId);
    List<Triaje> listarPorEstado(Boolean estado);
}
