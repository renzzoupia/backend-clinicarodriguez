package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.MedicosEspecialidades;
import java.util.List;

public interface MedicosEspecialidadesService {
    
    public List<MedicosEspecialidades> findAll();

    public MedicosEspecialidades findById(Long id);

    public MedicosEspecialidades save(MedicosEspecialidades medicosEspecialidades);

    public void delete(MedicosEspecialidades medicosEspecialidades);

    public void deleteById(Long id);
    
    // Métodos específicos para la relación
    public List<MedicosEspecialidades> findByMedicoId(Long medicoId);
    
    public List<MedicosEspecialidades> findByEspecialidadId(Long especialidadId);
    
    public boolean existsByMedicoAndEspecialidad(Long medicoId, Long especialidadId);
    
    public MedicosEspecialidades asignarEspecialidadAMedico(Long medicoId, Long especialidadId);
}

