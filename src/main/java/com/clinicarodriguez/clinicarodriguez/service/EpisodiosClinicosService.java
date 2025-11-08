package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.EpisodiosClinicos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EpisodiosClinicosService {

    // CRUD básico
    List<EpisodiosClinicos> listarTodos();
    Optional<EpisodiosClinicos> buscarPorId(Integer id);
    EpisodiosClinicos guardar(EpisodiosClinicos episodio);
    void eliminar(Integer id);

    // Métodos específicos
    List<EpisodiosClinicos> listarPorHistoria(Long historiaId);
    List<EpisodiosClinicos> listarActivosPorHistoria(Long historiaId);
    List<EpisodiosClinicos> listarPorTipo(String tipo);
    List<EpisodiosClinicos> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    EpisodiosClinicos obtenerUltimoEpisodioPorHistoria(Long historiaId);
    List<EpisodiosClinicos> listarPorEstado(Boolean estado);
    List<EpisodiosClinicos> buscarPorDiagnostico(String diagnostico);
}
