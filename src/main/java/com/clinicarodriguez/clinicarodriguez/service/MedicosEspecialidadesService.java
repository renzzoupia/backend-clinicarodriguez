package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.MedicosEspecialidades;
import java.util.List;

public interface MedicosEspecialidadesService {
    
    public List<MedicosEspecialidades> findAll();

    public MedicosEspecialidades findById(Integer id);

    public MedicosEspecialidades save(MedicosEspecialidades medicosEspecialidades);

    public void delete(MedicosEspecialidades medicosEspecialidades);

    public void deleteById(Integer id);
    
    // Métodos específicos para la relación
    public List<MedicosEspecialidades> findByMedicoId(Integer medicoId);
    
    public List<MedicosEspecialidades> findByEspecialidadId(Integer especialidadId);
    
    public boolean existsByMedicoAndEspecialidad(Integer medicoId, Integer especialidadId);
    
    public MedicosEspecialidades asignarEspecialidadAMedico(Integer medicoId, Integer especialidadId);
}

