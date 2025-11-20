package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import com.clinicarodriguez.clinicarodriguez.model.Personas;
import java.util.List;
import java.util.Optional;

public interface MedicosService {
    
    // CRUD básico
    List<Medicos> findAll();
    Medicos findById(Integer id);
    Medicos save(Medicos medico);
    void delete(Medicos medico);
    void deleteById(Integer id);
    
    // Métodos para crear médico completo (Persona + Medico)
    Medicos crearMedicoCompleto(Personas persona, String nroColegiatura);
    Medicos crearMedicoConUsuario(Personas persona, String username, String password, 
                                   String nroColegiatura);
    Medicos actualizarMedicoCompleto(Integer medicoId, Personas persona, String nroColegiatura, String rne);
    
    // Métodos personalizados
    Optional<Medicos> findByPersonaId(Integer personaId);
    Optional<Medicos> findByNroColegiatura(String nroColegiatura);
    List<Medicos> findByEstado(Boolean estado);
    List<Medicos> findAllActivosConPersona();
    List<Medicos> findAllConPersona();
    List<Medicos> findActivosByEspecialidadId(Integer especialidadId);
    
    // Validaciones
    boolean existsByNroColegiatura(String nroColegiatura);
    long countMedicosActivos();
    
    // Activar/Desactivar
    Medicos activar(Integer id);
    Medicos desactivar(Integer id);
}
