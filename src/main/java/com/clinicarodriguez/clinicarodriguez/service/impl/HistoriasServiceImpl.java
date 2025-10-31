package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Historias;
import com.clinicarodriguez.clinicarodriguez.repository.HistoriasRepository;
import com.clinicarodriguez.clinicarodriguez.service.HistoriasService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoriasServiceImpl implements HistoriasService {
    
    @Autowired
    private HistoriasRepository historiasRepository;

    @Override
    public List<Historias> findAll() {
        return historiasRepository.findAll();
    }

    @Override
    public Historias findById(Long id) {
        return historiasRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Historias save(Historias historia) {
        // Establecer fecha actual si no está definida
        
        return historiasRepository.save(historia);
    }

    @Transactional
    @Override
    public void delete(Historias historia) {
        historiasRepository.delete(historia);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        historiasRepository.deleteById(id);
    }

    @Override
    public List<Historias> findByPacienteId(Long pacienteId) {
        return historiasRepository.findByPacienteId(pacienteId);
    }

    @Override
    public List<Historias> findByUsuarioId(Long usuarioId) {
        return historiasRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Historias> findByHistFecha(LocalDate fecha) {
        return historiasRepository.findByHistFecha(fecha);
    }

    @Override
    public List<Historias> findByFechaRange(LocalDate fechaInicio, LocalDate fechaFin) {
        return historiasRepository.findByFechaRange(fechaInicio, fechaFin);
    }

    @Override
    public long countByPacienteId(Long pacienteId) {
        return historiasRepository.countByPacienteId(pacienteId);
    }
}
