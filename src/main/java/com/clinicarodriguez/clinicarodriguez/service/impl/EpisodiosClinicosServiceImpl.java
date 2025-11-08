package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.EpisodiosClinicos;
import com.clinicarodriguez.clinicarodriguez.repository.EpisodiosClinicosRepository;
import com.clinicarodriguez.clinicarodriguez.service.EpisodiosClinicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EpisodiosClinicosServiceImpl implements EpisodiosClinicosService {

    @Autowired
    private EpisodiosClinicosRepository episodiosClinicosRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EpisodiosClinicos> listarTodos() {
        return episodiosClinicosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EpisodiosClinicos> buscarPorId(Integer id) {
        return episodiosClinicosRepository.findById(id);
    }

    @Override
    @Transactional
    public EpisodiosClinicos guardar(EpisodiosClinicos episodio) {
        return episodiosClinicosRepository.save(episodio);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        episodiosClinicosRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpisodiosClinicos> listarPorHistoria(Long historiaId) {
        return episodiosClinicosRepository.findByHistoriaId(historiaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpisodiosClinicos> listarActivosPorHistoria(Long historiaId) {
        return episodiosClinicosRepository.findActivosByHistoriaId(historiaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpisodiosClinicos> listarPorTipo(String tipo) {
        return episodiosClinicosRepository.findByTipo(tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpisodiosClinicos> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return episodiosClinicosRepository.findByFechaRange(fechaInicio, fechaFin);
    }

    @Override
    @Transactional(readOnly = true)
    public EpisodiosClinicos obtenerUltimoEpisodioPorHistoria(Long historiaId) {
        return episodiosClinicosRepository.findUltimoEpisodioPorHistoria(historiaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpisodiosClinicos> listarPorEstado(Boolean estado) {
        return episodiosClinicosRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpisodiosClinicos> buscarPorDiagnostico(String diagnostico) {
        return episodiosClinicosRepository.findByDiagnosticoContaining(diagnostico);
    }
}
