package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Cita;
import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    
    // CRUD básico
    public List<Cita> findAll();
    
    public Cita findById(Integer id);
    
    public Cita save(Cita cita);
    
    public Cita update(Cita cita);
    
    public void delete(Cita cita);
    
    public void deleteById(Integer id);
    
    // Métodos personalizados
    public List<Cita> findByPacienteId(Integer pacienteId);
    
    public List<Cita> findByMedicoId(Integer medicoId);
    
    public List<Cita> findByCitaFecha(LocalDate fecha);
    
    public List<Cita> findByMedicoIdAndFecha(Integer medicoId, LocalDate fecha);
    
    public List<Cita> findByCitaEstado(String estado);
    
    public List<Cita> findByFechaRange(LocalDate fechaInicio, LocalDate fechaFin);
    
    public long countByMedicoAndFecha(Integer medicoId, LocalDate fecha);
    
    public List<Cita> findProximasCitasByPaciente(Integer pacienteId);
}
