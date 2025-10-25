package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Cita;
import com.clinicarodriguez.clinicarodriguez.repository.CitaRepository;
import com.clinicarodriguez.clinicarodriguez.service.CitaService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaServiceImpl implements CitaService {
    
    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    @Override
    public Cita findById(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Cita save(Cita cita) {
        // Establecer estado por defecto si no está definido
        if (cita.getCitaEstado() == null || cita.getCitaEstado().isEmpty()) {
            cita.setCitaEstado("PENDIENTE");
        }
        return citaRepository.save(cita);
    }

    @Transactional
    @Override
    public void delete(Cita cita) {
        citaRepository.delete(cita);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<Cita> findByPacienteId(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }

    @Override
    public List<Cita> findByUsuarioId(Long usuarioId) {
        return citaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Cita> findByCitaFecha(LocalDate fecha) {
        return citaRepository.findByCitaFecha(fecha);
    }

    @Override
    public List<Cita> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha) {
        return citaRepository.findByUsuarioIdAndFecha(usuarioId, fecha);
    }

    @Override
    public List<Cita> findByCitaEstado(String estado) {
        return citaRepository.findByCitaEstado(estado);
    }

    @Override
    public List<Cita> findByFechaRange(LocalDate fechaInicio, LocalDate fechaFin) {
        return citaRepository.findByFechaRange(fechaInicio, fechaFin);
    }

    @Override
    public long countByUsuarioAndFecha(Long usuarioId, LocalDate fecha) {
        return citaRepository.countByUsuarioAndFecha(usuarioId, fecha);
    }

    @Override
    public List<Cita> findProximasCitasByPaciente(Long pacienteId) {
        return citaRepository.findProximasCitasByPaciente(pacienteId, LocalDate.now());
    }
}
