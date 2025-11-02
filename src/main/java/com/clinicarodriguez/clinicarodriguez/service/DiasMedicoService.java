package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.DiasMedico;
import java.util.List;

public interface DiasMedicoService {
    
    public List<DiasMedico> findAll();
    
    public DiasMedico findById(Long id);
    
    public DiasMedico save(DiasMedico diasMedico);
    
    public void delete(DiasMedico diasMedico);
    
    public void deleteById(Long id);
    
    public List<DiasMedico> findByMedicoId(Long medicoId);
    
    public List<DiasMedico> findByDiaId(Integer diaId);
    
    public List<DiasMedico> findByMedicoIdAndEstadoActivo(Long medicoId);
}
