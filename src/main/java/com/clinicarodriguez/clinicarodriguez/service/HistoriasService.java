package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Historias;
import java.time.LocalDate;
import java.util.List;

public interface HistoriasService {
    
    public List<Historias> findAll();
    
    public Historias findById(Integer id);
    
    public Historias save(Historias historia);
    
    public void delete(Historias historia);
    
    public void deleteById(Integer id);
    
    public List<Historias> findByPacienteId(Integer pacienteId);
    
    public List<Historias> findByUsuarioId(Integer usuarioId);
    
    public long countByPacienteId(Integer pacienteId);
}
