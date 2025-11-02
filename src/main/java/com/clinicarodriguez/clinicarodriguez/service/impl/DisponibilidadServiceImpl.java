package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO;
import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO.MedicoDisponibilidadDTO;
import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO.HorarioDTO;
import com.clinicarodriguez.clinicarodriguez.model.Especialidades;
import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import com.clinicarodriguez.clinicarodriguez.model.MedicosEspecialidades;
import com.clinicarodriguez.clinicarodriguez.model.DiasMedico;
import com.clinicarodriguez.clinicarodriguez.repository.EspecialidadesRepository;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosEspecialidadesRepository;
import com.clinicarodriguez.clinicarodriguez.repository.DiasMedicoRepository;
import com.clinicarodriguez.clinicarodriguez.service.DisponibilidadService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisponibilidadServiceImpl implements DisponibilidadService {
    
    @Autowired
    private EspecialidadesRepository especialidadesRepository;
    
    @Autowired
    private MedicosEspecialidadesRepository medicosEspecialidadesRepository;
    
    @Autowired
    private DiasMedicoRepository diasMedicoRepository;
    
    @Override
    public DisponibilidadEspecialidadDTO obtenerDisponibilidadPorEspecialidad(Long especialidadId) {
        
        // 1. Obtener la especialidad
        Especialidades especialidad = especialidadesRepository.findById(especialidadId)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + especialidadId));
        
        // 2. Obtener médicos que tienen esa especialidad
        List<MedicosEspecialidades> medicosEspecialidades = 
                medicosEspecialidadesRepository.findByEspecialidadId(especialidadId);
        
        // 3. Por cada médico, obtener sus horarios disponibles
        List<MedicoDisponibilidadDTO> medicosDisponibles = new ArrayList<>();
        
        for (MedicosEspecialidades me : medicosEspecialidades) {
            Medicos medico = me.getMedico();
            
            // Solo incluir médicos activos
            if (medico.getMediEstado() != null && medico.getMediEstado().equalsIgnoreCase("ACTIVO")) {
                
                // Obtener horarios activos del médico
                List<DiasMedico> diasMedico = diasMedicoRepository.findByMedicoIdAndEstadoActivo(medico.getMediId());
                
                // Convertir a DTOs
                List<HorarioDTO> horariosDTO = diasMedico.stream()
                    .map(dm -> new HorarioDTO(
                        dm.getDia().getDiasId().longValue(),
                        dm.getDia().getDia().name(),
                        dm.getDimeHoraInicio(),
                        dm.getDimeHoraFin(),
                        dm.getDimeDuracion(),
                        dm.getDimeEstado()
                    ))
                    .collect(Collectors.toList());
                
                // Solo agregar médicos que tengan al menos un horario disponible
                if (!horariosDTO.isEmpty()) {
                    MedicoDisponibilidadDTO medicoDTO = new MedicoDisponibilidadDTO(
                        medico.getMediId(),
                        medico.getMediNombre(),
                        medico.getMediApellido(),
                        medico.getMediFotoUrl(),
                        medico.getMediEstado(),
                        horariosDTO
                    );
                    
                    medicosDisponibles.add(medicoDTO);
                }
            }
        }
        
        // 4. Construir y retornar el DTO de respuesta
        return new DisponibilidadEspecialidadDTO(
            especialidad.getEspeId(),
            especialidad.getEspeNombre(),
            especialidad.getEspeDescripcion(),
            medicosDisponibles
        );
    }
}
