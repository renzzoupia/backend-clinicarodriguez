package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Cita;
import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    
    // CRUD básico
    public List<Cita> findAll();
    
    public Cita findById(Long id);
    
    public Cita save(Cita cita);
    
    public void delete(Cita cita);
    
    public void deleteById(Long id);
    
    // Métodos personalizados
    public List<Cita> findByPacienteId(Long pacienteId);
    
    public List<Cita> findByUsuarioId(Long usuarioId);
    
    public List<Cita> findByCitaFecha(LocalDate fecha);
    
    public List<Cita> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha);
    
    public List<Cita> findByCitaEstado(String estado);
    
    public List<Cita> findByFechaRange(LocalDate fechaInicio, LocalDate fechaFin);
    
    public long countByUsuarioAndFecha(Long usuarioId, LocalDate fecha);
    
    public List<Cita> findProximasCitasByPaciente(Long pacienteId);
}
