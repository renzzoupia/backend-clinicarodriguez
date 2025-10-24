package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Especialidades;
import java.util.List;

public interface EspecialidadesService {
    public List<Especialidades> findAll();

    public Especialidades findById(Long id);

    public Especialidades save(Especialidades especialidad);

    public void delete(Especialidades especialidad);

    public void deleteById(Long id);
}

