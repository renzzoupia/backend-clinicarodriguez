package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Especialidades;
import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import com.clinicarodriguez.clinicarodriguez.model.MedicosEspecialidades;
import com.clinicarodriguez.clinicarodriguez.repository.EspecialidadesRepository;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosEspecialidadesRepository;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosRepository;
import com.clinicarodriguez.clinicarodriguez.service.MedicosEspecialidadesService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicosEspecialidadesServiceImpl implements MedicosEspecialidadesService {
    
    @Autowired
    private MedicosEspecialidadesRepository medicosEspecialidadesRepository;
    
    @Autowired
    private MedicosRepository medicosRepository;
    
    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    @Transactional
    @Override
    public List<MedicosEspecialidades> findAll() {
        return (List<MedicosEspecialidades>) medicosEspecialidadesRepository.findAll();
    }

    @Override
    public MedicosEspecialidades findById(Long id) {
        return medicosEspecialidadesRepository.findById(id).orElse(null);
    }

    @Override
    public MedicosEspecialidades save(MedicosEspecialidades medicosEspecialidades) {
        return medicosEspecialidadesRepository.save(medicosEspecialidades);
    }

    @Override
    public void delete(MedicosEspecialidades medicosEspecialidades) {
        medicosEspecialidadesRepository.delete(medicosEspecialidades);
    }

    @Override
    public void deleteById(Long id) {
        medicosEspecialidadesRepository.deleteById(id);
    }

    @Override
    public List<MedicosEspecialidades> findByMedicoId(Long medicoId) {
        return medicosEspecialidadesRepository.findByMedicoId(medicoId);
    }

    @Override
    public List<MedicosEspecialidades> findByEspecialidadId(Long especialidadId) {
        return medicosEspecialidadesRepository.findByEspecialidadId(especialidadId);
    }

    @Override
    public boolean existsByMedicoAndEspecialidad(Long medicoId, Long especialidadId) {
        return medicosEspecialidadesRepository.existsByMedicoAndEspecialidad(medicoId, especialidadId);
    }

    @Transactional
    @Override
    public MedicosEspecialidades asignarEspecialidadAMedico(Long medicoId, Long especialidadId) {
        // Verificar si ya existe la relación
        if (existsByMedicoAndEspecialidad(medicoId, especialidadId)) {
            return null; // Ya existe
        }
        
        // Buscar el médico y la especialidad
        Medicos medico = medicosRepository.findById(medicoId).orElse(null);
        Especialidades especialidad = especialidadesRepository.findById(especialidadId).orElse(null);
        
        if (medico == null || especialidad == null) {
            return null; // No se encontró el médico o la especialidad
        }
        
        // Crear la relación
        MedicosEspecialidades medicosEspecialidades = new MedicosEspecialidades();
        medicosEspecialidades.setMedico(medico);
        medicosEspecialidades.setEspecialidad(especialidad);
        
        return medicosEspecialidadesRepository.save(medicosEspecialidades);
    }
}

