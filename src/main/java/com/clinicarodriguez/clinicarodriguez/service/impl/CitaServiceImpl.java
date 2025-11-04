package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Cita;
import com.clinicarodriguez.clinicarodriguez.model.DiasMedico;
import com.clinicarodriguez.clinicarodriguez.repository.CitaRepository;
import com.clinicarodriguez.clinicarodriguez.repository.DiasMedicoRepository;
import com.clinicarodriguez.clinicarodriguez.service.CitaService;
import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaServiceImpl implements CitaService {
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private DiasMedicoRepository diasMedicoRepository;

    @Override
    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    @Override
    public Cita findById(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Cita save(Cita cita) {
        // Validaciones básicas
        if (cita.getMedico() == null || cita.getMedico().getMediId() == null) {
            throw new RuntimeException("El médico es obligatorio para crear una cita");
        }
        if (cita.getCitaFecha() == null) {
            throw new RuntimeException("La fecha es obligatoria para crear una cita");
        }
        if (cita.getCitaHora() == null) {
            throw new RuntimeException("La hora es obligatoria para crear una cita");
        }
        
        // Calcular cita_hora_fin automáticamente si no está definida
        if (cita.getCitaHoraFin() == null) {
            // Obtener el día de la semana
            DayOfWeek diaSemana = cita.getCitaFecha().getDayOfWeek();
            int diaId = diaSemana.getValue();
            
            // Buscar configuración del médico para ese día
            List<DiasMedico> diasMedico = diasMedicoRepository
                    .findByMedicoIdAndEstadoActivo(cita.getMedico().getMediId());
            
            DiasMedico diaConfig = diasMedico.stream()
                    .filter(dm -> dm.getDia().getDiasId() == diaId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                        "El médico no tiene configuración de horario para el día: " + diaSemana));
            
            // Calcular hora fin
            Integer duracion = diaConfig.getDimeDuracion();
            LocalTime horaFin = cita.getCitaHora().plusMinutes(duracion);
            cita.setCitaHoraFin(horaFin);
        }
        
        // Validar que no haya solapamiento con otras citas
        boolean haySolapamiento = citaRepository.existeSolapamiento(
            cita.getMedico().getMediId(),
            cita.getCitaFecha(),
            cita.getCitaHora(),
            cita.getCitaHoraFin()
        );
        
        if (haySolapamiento) {
            throw new RuntimeException(
                "Ya existe una cita agendada en ese horario. Por favor, seleccione otro horario.");
        }
        
        // Establecer estado por defecto si no está definido
        if (cita.getCitaEstado() == null || cita.getCitaEstado().isEmpty()) {
            cita.setCitaEstado("PENDIENTE");
        }
        
        // Establecer fecha de registro
        if (cita.getCitaFechaRegistro() == null) {
            cita.setCitaFechaRegistro(LocalDate.now());
        }
        
        return citaRepository.save(cita);
    }

    @Transactional
    @Override
    public void delete(Cita cita) {
        citaRepository.delete(cita);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<Cita> findByPacienteId(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }

    @Override
    public List<Cita> findByMedicoId(Long medicoId) {
        return citaRepository.findByMedicoId(medicoId);
    }

    @Override
    public List<Cita> findByCitaFecha(LocalDate fecha) {
        return citaRepository.findByCitaFecha(fecha);
    }

    @Override
    public List<Cita> findByMedicoIdAndFecha(Long medicoId, LocalDate fecha) {
        return citaRepository.findByMedicoIdAndFecha(medicoId, fecha);
    }

    @Override
    public List<Cita> findByCitaEstado(String estado) {
        return citaRepository.findByCitaEstado(estado);
    }

    @Override
    public List<Cita> findByFechaRange(LocalDate fechaInicio, LocalDate fechaFin) {
        return citaRepository.findByFechaRange(fechaInicio, fechaFin);
    }

    @Override
    public long countByMedicoAndFecha(Long medicoId, LocalDate fecha) {
        return citaRepository.countByMedicoAndFecha(medicoId, fecha);
    }

    @Override
    public List<Cita> findProximasCitasByPaciente(Long pacienteId) {
        return citaRepository.findProximasCitasByPaciente(pacienteId, LocalDate.now());
    }
}
