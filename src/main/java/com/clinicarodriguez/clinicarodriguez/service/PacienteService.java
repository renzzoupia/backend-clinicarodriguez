package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import com.clinicarodriguez.clinicarodriguez.model.Personas;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    
    // CRUD básico
    List<Paciente> findAll();
    Paciente findById(Integer id);
    Paciente save(Paciente paciente);
    void delete(Paciente paciente);
    void deleteById(Integer id);
    
    // Métodos para crear paciente completo (Persona + Paciente)
    Paciente crearPacienteCompleto(Personas persona);
    Paciente crearPacienteConApoderado(Personas persona, Integer apoderadoPersId);
    Paciente actualizarPacienteCompleto(Integer pacienteId, Personas persona, String nroColegiatura,
                                        String grupoSanguineo, String alergias);
    
    // Métodos personalizados
    Optional<Paciente> findByPersonaId(Integer personaId);
    
    List<Paciente> findByEstado(Integer estado);
    List<Paciente> findAllActivosConPersona();
    List<Paciente> findAllConPersona();
    List<Paciente> buscarPorDni(String dniBusqueda, int limite);
    List<Paciente> buscarPorDniSinHistoria(String dniBusqueda, int limite);
    Optional<Paciente> findByDni(String dni);
    
    // Validaciones
    long countPacientesActivos();
    
    // Activar/Desactivar
    Paciente activar(Integer id);
    Paciente desactivar(Integer id);
    
    // Método para generar número de colegiatura automático
    String generarNroColegiatura();
}
