package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import com.clinicarodriguez.clinicarodriguez.repository.PacienteRepository;
import com.clinicarodriguez.clinicarodriguez.service.PacienteService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl implements PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    @Override
    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente findById(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Paciente save(Paciente paciente) {
        // Generar número de historia si no existe
        if (paciente.getPaciNumhistoria() == null || paciente.getPaciNumhistoria().isEmpty()) {
            paciente.setPaciNumhistoria(generarNumHistoria());
        }
        
        // Establecer estado activo por defecto si no está definido
        if (paciente.getPaciEstado() == null) {
            paciente.setPaciEstado(1); // 1 = Activo
        }
        
        return pacienteRepository.save(paciente);
    }

    @Transactional
    @Override
    public void delete(Paciente paciente) {
        pacienteRepository.delete(paciente);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public Optional<Paciente> findByDni(String dni) {
        return pacienteRepository.findByPaciDni(dni);
    }

    @Override
    public Optional<Paciente> findByEmail(String email) {
        return pacienteRepository.findByPaciEmail(email);
    }

    @Override
    public Optional<Paciente> findByNumHistoria(String numHistoria) {
        return pacienteRepository.findByPaciNumhistoria(numHistoria);
    }

    @Override
    public List<Paciente> findByEstado(Integer estado) {
        return pacienteRepository.findByPaciEstado(estado);
    }

    @Override
    public List<Paciente> findByNombre(String nombre) {
        return pacienteRepository.findByNombreContaining(nombre);
    }

    @Override
    public boolean existsByDni(String dni) {
        return pacienteRepository.existsByPaciDni(dni);
    }

    @Override
    public boolean existsByEmail(String email) {
        return pacienteRepository.existsByPaciEmail(email);
    }

    @Override
    public long countPacientesActivos() {
        return pacienteRepository.countPacientesActivos();
    }

    @Override
    public String generarNumHistoria() {
        // Formato: HC-YYYYMMDD-XXXX
        // Ejemplo: HC-20250124-0001
        LocalDate hoy = LocalDate.now();
        String fecha = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        // Contar pacientes del día (simplificado)
        long totalPacientes = pacienteRepository.count();
        String secuencia = String.format("%04d", totalPacientes + 1);
        
        return "HC-" + fecha + "-" + secuencia;
    }
}
