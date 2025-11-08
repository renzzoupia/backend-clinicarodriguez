package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Triaje;
import com.clinicarodriguez.clinicarodriguez.repository.TriajeRepository;
import com.clinicarodriguez.clinicarodriguez.service.TriajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TriajeServiceImpl implements TriajeService {

    @Autowired
    private TriajeRepository triajeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Triaje> listarTodos() {
        return triajeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Triaje> buscarPorId(Long id) {
        return triajeRepository.findById(id);
    }

    @Override
    @Transactional
    public Triaje guardar(Triaje triaje) {
        return triajeRepository.save(triaje);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        triajeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Triaje> listarPorHistoria(Long historiaId) {
        return triajeRepository.findByHistoriaId(historiaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Triaje> listarActivosPorHistoria(Long historiaId) {
        return triajeRepository.findActivosByHistoriaId(historiaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Triaje> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return triajeRepository.findByFechaRange(fechaInicio, fechaFin);
    }

    @Override
    @Transactional(readOnly = true)
    public Triaje obtenerUltimoTriajePorHistoria(Long historiaId) {
        return triajeRepository.findUltimoTriajePorHistoria(historiaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Triaje> listarPorEstado(Boolean estado) {
        return triajeRepository.findByEstado(estado);
    }
}
