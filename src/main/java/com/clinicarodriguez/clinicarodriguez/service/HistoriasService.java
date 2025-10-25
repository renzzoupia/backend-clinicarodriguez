package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Historias;
import java.time.LocalDate;
import java.util.List;

public interface HistoriasService {
    
    public List<Historias> findAll();
    
    public Historias findById(Long id);
    
    public Historias save(Historias historia);
    
    public void delete(Historias historia);
    
    public void deleteById(Long id);
    
    public List<Historias> findByPacienteId(Long pacienteId);
    
    public List<Historias> findByUsuarioId(Long usuarioId);
    
    public List<Historias> findByHistFecha(LocalDate fecha);
    
    public List<Historias> findByFechaRange(LocalDate fechaInicio, LocalDate fechaFin);
    
    public long countByPacienteId(Long pacienteId);
}
