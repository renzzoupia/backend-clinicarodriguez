package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.model.Personas.TipoDocumento;
import com.clinicarodriguez.clinicarodriguez.repository.PersonasRepository;
import com.clinicarodriguez.clinicarodriguez.service.PersonasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonasServiceImpl implements PersonasService {

    @Autowired
    private PersonasRepository personasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Personas> listarTodas() {
        return personasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personas> buscarPorId(Integer id) {
        return personasRepository.findById(id);
    }

    @Override
    @Transactional
    public Personas guardar(Personas persona) {
        return personasRepository.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        personasRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Personas actualizar(Integer id, Personas persona) {
        Optional<Personas> personaExistente = personasRepository.findById(id);
        if (personaExistente.isPresent()) {
            persona.setPersId(id);
            return personasRepository.save(persona);
        }
        throw new RuntimeException("Persona no encontrada con id: " + id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personas> buscarPorTipoDocYNroDoc(TipoDocumento tipoDoc, String nroDoc) {
        return personasRepository.findByTipoDocAndNroDoc(tipoDoc, nroDoc);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorTipoDocYNroDoc(TipoDocumento tipoDoc, String nroDoc) {
        return personasRepository.existsByTipoDocAndNroDoc(tipoDoc, nroDoc);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personas> buscarPorEmail(String email) {
        return personasRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorEmail(String email) {
        return personasRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personas> buscarPorNroDoc(String nroDoc) {
        return personasRepository.findByNroDoc(nroDoc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personas> buscarPorNombre(String nombre) {
        return personasRepository.findByNombreContaining(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personas> buscarPorTelefono(String telefono) {
        return personasRepository.findByTelefono(telefono);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personas> listarActivos() {
        return personasRepository.findAllActivos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personas> listarPorEstado(Boolean estado) {
        return personasRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarActivos() {
        return personasRepository.countActivos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personas> listarPersonasSinUsuario() {
        return personasRepository.findPersonasSinUsuario();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personas> listarPersonasSinPaciente() {
        return personasRepository.findPersonasSinPaciente();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personas> listarPersonasSinMedico() {
        return personasRepository.findPersonasSinMedico();
    }

    @Override
    @Transactional
    public Personas activar(Integer id) {
        Optional<Personas> persona = personasRepository.findById(id);
        if (persona.isPresent()) {
            Personas p = persona.get();
            p.setPersEsActivo(true);
            return personasRepository.save(p);
        }
        throw new RuntimeException("Persona no encontrada con id: " + id);
    }

    @Override
    @Transactional
    public Personas desactivar(Integer id) {
        Optional<Personas> persona = personasRepository.findById(id);
        if (persona.isPresent()) {
            Personas p = persona.get();
            p.setPersEsActivo(false);
            return personasRepository.save(p);
        }
        throw new RuntimeException("Persona no encontrada con id: " + id);
    }

    @Override
    @Transactional(readOnly = true)
    public Personas findById(Integer id) {
        return personasRepository.findById(id).orElse(null);
    }
}
