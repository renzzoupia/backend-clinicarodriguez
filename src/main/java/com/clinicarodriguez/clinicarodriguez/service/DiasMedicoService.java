package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.DiasMedico;
import java.util.List;

public interface DiasMedicoService {
    
    public List<DiasMedico> findAll();
    
    public DiasMedico findById(Integer id);
    
    public DiasMedico save(DiasMedico diasMedico);
    
    public void delete(DiasMedico diasMedico);
    
    public void deleteById(Integer id);
    
    public List<DiasMedico> findByMedicoId(Integer medicoId);
    
    public List<DiasMedico> findByDiaId(Integer diaId);
    
    public List<DiasMedico> findByMedicoIdAndEstadoActivo(Integer medicoId);
}
