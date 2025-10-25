package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    
    // CRUD básico
    public List<Paciente> findAll();
    
    public Paciente findById(Long id);
    
    public Paciente save(Paciente paciente);
    
    public void delete(Paciente paciente);
    
    public void deleteById(Long id);
    
    // Métodos personalizados
    public Optional<Paciente> findByDni(String dni);
    
    public Optional<Paciente> findByEmail(String email);
    
    public Optional<Paciente> findByNumHistoria(String numHistoria);
    
    public List<Paciente> findByEstado(Integer estado);
    
    public List<Paciente> findByNombre(String nombre);
    
    public boolean existsByDni(String dni);
    
    public boolean existsByEmail(String email);
    
    public long countPacientesActivos();
    
    // Método para generar número de historia automático
    public String generarNumHistoria();
}
