package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Especialidades;
import com.clinicarodriguez.clinicarodriguez.repository.EspecialidadesRepository;
import com.clinicarodriguez.clinicarodriguez.service.EspecialidadesService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadesServiceImpl implements EspecialidadesService {
    
    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    @Transactional
    @Override
    public List<Especialidades> findAll() {
        return (List<Especialidades>) especialidadesRepository.findAll();
    }

    @Override
    public Especialidades findById(Long id) {
        return especialidadesRepository.findById(id).orElse(null);
    }

    @Override
    public Especialidades save(Especialidades especialidad) {
        return especialidadesRepository.save(especialidad);
    }

    @Override
    public void delete(Especialidades especialidad) {
        especialidadesRepository.delete(especialidad);
    }

    @Override
    public void deleteById(Long id) {
        especialidadesRepository.deleteById(id);
    }
}

