package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosRepository;
import com.clinicarodriguez.clinicarodriguez.service.MedicosService;
import com.clinicarodriguez.clinicarodriguez.service.PersonasService;
import com.clinicarodriguez.clinicarodriguez.service.UsuariosService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicosServiceImpl implements MedicosService {
    
    @Autowired
    private MedicosRepository medicosRepository;
    
    @Autowired
    private PersonasService personasService;
    
    @Autowired
    private UsuariosService usuariosService;

    @Override
    public List<Medicos> findAll() {
        return medicosRepository.findAll();
    }

    @Override
    public Medicos findById(Integer id) {
        return medicosRepository.findById(id).orElse(null);
    }

    @Override
    public Medicos save(Medicos medico) {
        return medicosRepository.save(medico);
    }

    @Override
    public void delete(Medicos medico) {
        medicosRepository.delete(medico);
    }

    @Override
    public void deleteById(Integer id) {
        medicosRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Medicos crearMedicoCompleto(Personas persona, String nroColegiatura) {
        // 1. Validar documento único
        System.out.println("DEBUG MEDICO crearMedicoCompleto - Validando documento: Tipo=" + persona.getPersTipoDoc() + ", Nro=" + persona.getPersNroDoc());
        //boolean existeDocumento = personasService.existePorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc());
        boolean existeDocumento = true;
        System.out.println("DEBUG MEDICO crearMedicoCompleto - Existe documento: " + existeDocumento);
        
        if (existeDocumento) {
            Optional<Personas> personaExistente = personasService.buscarPorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc());
            if (personaExistente.isPresent()) {
                Personas p = personaExistente.get();
                System.out.println("DEBUG MEDICO - Persona existente ID: " + p.getPersId() + ", Nombre: " + p.getPersNombrecompleto());
                throw new RuntimeException("Ya existe una persona con ese documento (" + 
                    persona.getPersTipoDoc() + "-" + persona.getPersNroDoc() + 
                    "). Persona ID: " + p.getPersId() + ", Nombre: " + p.getPersNombrecompleto());
            }
            throw new RuntimeException("Ya existe una persona con ese documento (" + 
                persona.getPersTipoDoc() + "-" + persona.getPersNroDoc() + ")");
        }
        
        // 2. Validar nro colegiatura único
        if (nroColegiatura != null && !nroColegiatura.isEmpty()) {
            if (existsByNroColegiatura(nroColegiatura)) {
                throw new RuntimeException("Ya existe un médico con ese número de colegiatura");
            }
        } else {
            throw new RuntimeException("El número de colegiatura es obligatorio para médicos");
        }
        
        // 3. Crear la persona primero
        Personas personaGuardada = personasService.guardar(persona);
        
        // 4. Crear el médico vinculado a la persona
        Medicos medico = new Medicos();
        medico.setPersona(personaGuardada);
        medico.setMediNroColegiatura(nroColegiatura);
        medico.setMediEstado(true);
        
        return medicosRepository.save(medico);
    }

    @Transactional
    @Override
    public Medicos crearMedicoConUsuario(Personas persona, String username, String password,
                                         String nroColegiatura) {
        // 1. Validar documento único
        System.out.println("DEBUG - xdValidando documento: Tipo=" + persona.getPersTipoDoc() + ", Nro=" + persona.getPersNroDoc());
        boolean existeDocumento = personasService.existePorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc());
        System.out.println("DEBUG -  xd Existe documento: " + existeDocumento);
        
        if (existeDocumento) {
            // Buscar la persona existente para más detalles
            Optional<Personas> personaExistente = personasService.buscarPorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc());
            if (personaExistente.isPresent()) {
                Personas p = personaExistente.get();
                System.out.println("DEBUG - Persona existente ID: " + p.getPersId() + ", Nombre: " + p.getPersNombrecompleto());
                throw new RuntimeException("Ya existe una persona con ese documento (" + 
                    persona.getPersTipoDoc() + "-" + persona.getPersNroDoc() + 
                    "). Persona ID: " + p.getPersId() + ", Nombre: " + p.getPersNombrecompleto());
            }
            throw new RuntimeException("Ya existe una persona con ese documento (" + 
                persona.getPersTipoDoc() + "-" + persona.getPersNroDoc() + ")");
        }
        
        // 2. Validar username único
        if (usuariosService.existsByUsername(username)) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        
        // 3. Validar nro colegiatura único
        if (nroColegiatura != null && !nroColegiatura.isEmpty()) {
            if (existsByNroColegiatura(nroColegiatura)) {
                throw new RuntimeException("Ya existe un médico con ese número de colegiatura");
            }
        } else {
            throw new RuntimeException("El número de colegiatura es obligatorio para médicos");
        }
        
        // 4. Crear la persona primero
        Personas personaGuardada = personasService.guardar(persona);
        System.out.println("DEBUG - Persona creada con ID: " + personaGuardada.getPersId());
        
        // 5. Crear el usuario vinculado a la persona existente (sin validar documento nuevamente)
        Usuarios usuario = usuariosService.crearUsuarioParaPersonaExistente(personaGuardada, username, password);
        System.out.println("DEBUG - Usuario creado con ID: " + usuario.getUsuaId());
        
        // 6. Crear el médico vinculado a la persona y al usuario
        Medicos medico = new Medicos();
        medico.setPersona(personaGuardada);
        medico.setUsuarios(usuario);
        medico.setMediNroColegiatura(nroColegiatura);
        medico.setMediEstado(true);
        
        return medicosRepository.save(medico);
    }

    @Transactional
    @Override
    public Medicos actualizarMedicoCompleto(Integer medicoId, Personas persona, String nroColegiatura, String rne) {
        // 1. Buscar médico existente
        Medicos medicoExistente = findById(medicoId);
        if (medicoExistente == null) {
            throw new RuntimeException("Médico no encontrado");
        }
        
        // 2. Actualizar datos de la persona
        persona.setPersId(medicoExistente.getPersona().getPersId());
        personasService.actualizar(persona.getPersId(), persona);
        
        // 3. Actualizar datos del médico
        if (nroColegiatura != null && !nroColegiatura.equals(medicoExistente.getMediNroColegiatura())) {
            if (existsByNroColegiatura(nroColegiatura)) {
                throw new RuntimeException("El nuevo número de colegiatura ya existe");
            }
            medicoExistente.setMediNroColegiatura(nroColegiatura);
        }
        
        
        return medicosRepository.save(medicoExistente);
    }

    @Override
    public Optional<Medicos> findByPersonaId(Integer personaId) {
        return medicosRepository.findByPersonaId(personaId);
    }

    @Override
    public Optional<Medicos> findByNroColegiatura(String nroColegiatura) {
        return medicosRepository.findByMediNroColegiatura(nroColegiatura);
    }

    @Override
    public List<Medicos> findByEstado(Boolean estado) {
        return medicosRepository.findByEstado(estado);
    }

    @Override
    public List<Medicos> findAllActivosConPersona() {
        return medicosRepository.findAllActivosConPersona();
    }

    @Override
    public List<Medicos> findAllConPersona() {
        return medicosRepository.findAllConPersona();
    }

    @Override
    public List<Medicos> findActivosByEspecialidadId(Integer especialidadId) {
        return medicosRepository.findActivosByEspecialidadId(especialidadId);
    }

    @Override
    public boolean existsByNroColegiatura(String nroColegiatura) {
        return medicosRepository.existsByMediNroColegiatura(nroColegiatura);
    }

    @Override
    public long countMedicosActivos() {
        return medicosRepository.countMedicosActivos();
    }

    @Transactional
    @Override
    public Medicos activar(Integer id) {
        Medicos medico = findById(id);
        if (medico == null) {
            throw new RuntimeException("Médico no encontrado");
        }
        medico.setMediEstado(true);
        return medicosRepository.save(medico);
    }

    @Transactional
    @Override
    public Medicos desactivar(Integer id) {
        Medicos medico = findById(id);
        if (medico == null) {
            throw new RuntimeException("Médico no encontrado");
        }
        medico.setMediEstado(false);
        return medicosRepository.save(medico);
    }
}
