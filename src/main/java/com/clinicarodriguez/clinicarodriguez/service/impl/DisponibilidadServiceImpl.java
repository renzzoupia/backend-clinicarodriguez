package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO;
import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO.MedicoDisponibilidadDTO;
import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO.HorarioDTO;
import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO.SlotOcupadoDTO;
import com.clinicarodriguez.clinicarodriguez.dto.SlotsDisponiblesDTO;
import com.clinicarodriguez.clinicarodriguez.dto.SlotsDisponiblesDTO.ConfiguracionHorarioDTO;
import com.clinicarodriguez.clinicarodriguez.dto.SlotHorarioDTO;
import com.clinicarodriguez.clinicarodriguez.model.Cita;
import com.clinicarodriguez.clinicarodriguez.model.Especialidades;
import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import com.clinicarodriguez.clinicarodriguez.model.MedicosEspecialidades;
import com.clinicarodriguez.clinicarodriguez.model.DiasMedico;
import com.clinicarodriguez.clinicarodriguez.repository.CitaRepository;
import com.clinicarodriguez.clinicarodriguez.repository.EspecialidadesRepository;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosEspecialidadesRepository;
import com.clinicarodriguez.clinicarodriguez.repository.DiasMedicoRepository;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosRepository;
import com.clinicarodriguez.clinicarodriguez.service.DisponibilidadService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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
    
    @Autowired
    private MedicosRepository medicosRepository;
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Override
    public DisponibilidadEspecialidadDTO obtenerDisponibilidadPorEspecialidad(Integer especialidadId) {
        
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
            if (medico.getMediEstado() != null && medico.getMediEstado()) {
                
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
                        medico.getPersona().getPersNombrecompleto(),
                        "",
                        medico.getPersona().getPersFotoUrl(),
                        medico.getMediEstado() ? "ACTIVO" : "INACTIVO",
                        horariosDTO
                    );
                    
                    // Obtener slots ocupados (citas de los próximos 30 días)
                    LocalDate fechaInicio = LocalDate.now();
                    LocalDate fechaFin = fechaInicio.plusDays(30);
                    
                    List<Cita> citasFuturas = citaRepository.findByFechaRange(fechaInicio, fechaFin)
                            .stream()
                            .filter(c -> c.getMedico().getMediId().equals(medico.getMediId()))
                            .filter(c -> !"CANCELADA".equalsIgnoreCase(c.getCitaEstado()))
                            .collect(Collectors.toList());
                    
                    List<SlotOcupadoDTO> slotsOcupados = citasFuturas.stream()
                            .map(cita -> new SlotOcupadoDTO(
                                cita.getCitaFecha(),
                                cita.getCitaHora(),
                                cita.getCitaHoraFin(),
                                cita.getCitaId()
                            ))
                            .collect(Collectors.toList());
                    
                    medicoDTO.setSlotsOcupados(slotsOcupados);
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
    
    @Override
    public SlotsDisponiblesDTO obtenerSlotsDisponibles(Integer medicoId, LocalDate fecha) {
        
        // 1. Verificar que el médico existe
        Medicos medico = medicosRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con id: " + medicoId));
        
        // 2. Obtener el día de la semana de la fecha
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        // Convertir DayOfWeek a ID (1=LUNES, 2=MARTES, etc.)
        int diaId = diaSemana.getValue();
        
        // 3. Buscar la configuración de horario del médico para ese día
        List<DiasMedico> diasMedico = diasMedicoRepository.findByMedicoIdAndEstadoActivo(medicoId);
        
        // Filtrar por el día específico
        DiasMedico diaConfig = diasMedico.stream()
                .filter(dm -> dm.getDia().getDiasId() == diaId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                    "El médico no tiene configuración de horario para el día: " + diaSemana));
        
        LocalTime horaInicio = diaConfig.getDimeHoraInicio();
        LocalTime horaFin = diaConfig.getDimeHoraFin();
        Integer duracion = diaConfig.getDimeDuracion();
        
        // 4. Obtener todas las citas activas del médico para esa fecha
        List<Cita> citasActivas = citaRepository.findCitasActivasByMedicoAndFecha(medicoId, fecha);
        
        // 5. Generar todos los slots posibles
        List<SlotHorarioDTO> slots = new ArrayList<>();
        LocalTime horaActual = horaInicio;
        
        while (horaActual.isBefore(horaFin)) {
            LocalTime horaFinSlot = horaActual.plusMinutes(duracion);
            
            // Verificar si el slot está ocupado por alguna cita
            final LocalTime horaSlot = horaActual;
            boolean ocupado = citasActivas.stream()
                    .anyMatch(cita -> 
                        // El slot está ocupado si hay solapamiento
                        (cita.getCitaHora().isBefore(horaFinSlot) && 
                         cita.getCitaHoraFin().isAfter(horaSlot))
                    );
            
            // Obtener el ID de la cita que ocupa el slot (si existe)
            Integer citaId = null;
            if (ocupado) {
                citaId = citasActivas.stream()
                        .filter(cita -> 
                            cita.getCitaHora().isBefore(horaFinSlot) && 
                            cita.getCitaHoraFin().isAfter(horaSlot))
                        .findFirst()
                        .map(Cita::getCitaId)
                        .orElse(null);
            }
            
            // Crear el slot
            SlotHorarioDTO slot = new SlotHorarioDTO(horaActual, horaFinSlot, !ocupado, citaId);
            slots.add(slot);
            
            // Avanzar al siguiente slot
            horaActual = horaFinSlot;
        }
        
        // 6. Crear configuración del horario
        ConfiguracionHorarioDTO configuracion = new ConfiguracionHorarioDTO(
            horaInicio, horaFin, duracion
        );
        
        // 7. Construir nombre completo del médico
        String nombreCompleto = medico.getPersona().getPersNombrecompleto();
        
        // 8. Construir y retornar el DTO de respuesta
        return new SlotsDisponiblesDTO(
            medicoId,
            nombreCompleto,
            fecha,
            configuracion,
            slots
        );
    }
}
