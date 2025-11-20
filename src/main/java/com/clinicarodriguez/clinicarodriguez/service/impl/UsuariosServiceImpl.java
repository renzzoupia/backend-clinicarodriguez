package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.repository.UsuariosRepository;
import com.clinicarodriguez.clinicarodriguez.service.PersonasService;
import com.clinicarodriguez.clinicarodriguez.service.UsuariosService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuariosServiceImpl implements UsuariosService {
    
    @Autowired
    private UsuariosRepository usuariosRepository;
    
    @Autowired
    private PersonasService personasService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public List<Usuarios> findAll() {
        return usuariosRepository.findAll();
    }

    @Override
    public Usuarios findById(Integer id) {
        return usuariosRepository.findById(id).orElse(null);
    }

    @Override
    public Usuarios save(Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }

    @Override
    public void delete(Usuarios usuario) {
        usuariosRepository.delete(usuario);
    }

    @Override
    public void deleteById(Integer id) {
        usuariosRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Usuarios crearUsuarioCompleto(Personas persona, String username, String password) {
        // 1. Validar que el username no exista
        if (existsByUsername(username)) {
            throw new RuntimeException("El username ya existe");
        }
        
        // 2. Validar documento único
        if (personasService.existePorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc())) {
            throw new RuntimeException("Ya existe una persona con ese documento");
        }
        
        // 3. Crear la persona primero
        Personas personaGuardada = personasService.guardar(persona);
        
        // 4. Crear el usuario vinculado a la persona
        Usuarios usuario = new Usuarios();
        usuario.setPersona(personaGuardada);
        usuario.setUsuaUsername(username);
        usuario.setUsuaClave(passwordEncoder.encode(password));
        usuario.setUsuaEstado(true);
        
        return usuariosRepository.save(usuario);
    }

    @Transactional
    @Override
    public Usuarios crearUsuarioParaPersonaExistente(Personas personaExistente, String username, String password) {
        // 1. Validar que la persona exista y tenga ID
        if (personaExistente == null || personaExistente.getPersId() == null) {
            throw new RuntimeException("La persona debe estar guardada en la base de datos primero");
        }
        
        // 2. Validar que el username no exista
        if (existsByUsername(username)) {
            throw new RuntimeException("El username ya existe");
        }
        
        // 3. Validar que la persona no tenga ya un usuario asignado
        Optional<Usuarios> usuarioExistente = findByPersonaId(personaExistente.getPersId());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("La persona ya tiene un usuario asociado");
        }
        
        // 4. Crear el usuario vinculado a la persona existente
        Usuarios usuario = new Usuarios();
        usuario.setPersona(personaExistente);
        usuario.setUsuaUsername(username);
        usuario.setUsuaClave(passwordEncoder.encode(password));
        usuario.setUsuaEstado(true);
        
        return usuariosRepository.save(usuario);
    }

    @Transactional
    @Override
    public Usuarios actualizarUsuarioCompleto(Integer usuarioId, Personas persona, String username, String newPassword) {
        // 1. Buscar usuario existente
        Usuarios usuarioExistente = findById(usuarioId);
        if (usuarioExistente == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        // 2. Actualizar datos de la persona
        persona.setPersId(usuarioExistente.getPersona().getPersId());
        personasService.actualizar(persona.getPersId(), persona);
        
        // 3. Actualizar username si cambió
        if (username != null && !username.equals(usuarioExistente.getUsuaUsername())) {
            if (existsByUsername(username)) {
                throw new RuntimeException("El nuevo username ya existe");
            }
            usuarioExistente.setUsuaUsername(username);
        }
        
        // 4. Actualizar contraseña si se proporciona una nueva
        if (newPassword != null && !newPassword.isEmpty()) {
            usuarioExistente.setUsuaClave(passwordEncoder.encode(newPassword));
        }
        
        return usuariosRepository.save(usuarioExistente);
    }

    @Transactional
    @Override
    public Usuarios registrarUsuario(Usuarios usuario) {
        // Encriptar la contraseña antes de guardar
        String claveEncriptada = passwordEncoder.encode(usuario.getUsuaClave());
        usuario.setUsuaClave(claveEncriptada);
        
        if (usuario.getUsuaEstado() == null) {
            usuario.setUsuaEstado(true);
        }
        
        return usuariosRepository.save(usuario);
    }

    @Override
    public Optional<Usuarios> findByUsername(String username) {
        return usuariosRepository.findByUsuaUsername(username);
    }

    @Override
    public Optional<Usuarios> findByPersonaId(Integer personaId) {
        return usuariosRepository.findByPersonaId(personaId);
    }

    @Override
    public boolean validarCredenciales(String username, String passwordTextoPlano) {
        Optional<Usuarios> usuarioOpt = usuariosRepository.findByUsuaUsername(username);
        
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        
        Usuarios usuario = usuarioOpt.get();
        
        // Verificar que el usuario esté activo
        if (!usuario.getUsuaEstado()) {
            return false;
        }
        
        // Comparar la contraseña en texto plano con la encriptada
        return passwordEncoder.matches(passwordTextoPlano, usuario.getUsuaClave());
    }

    @Override
    public boolean existsByUsername(String username) {
        return usuariosRepository.existsByUsuaUsername(username);
    }

    @Transactional
    @Override
    public void actualizarUltimaSesion(Integer usuarioId) {
        Optional<Usuarios> usuarioOpt = usuariosRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            usuario.setUsuaUltimaSesion(LocalDateTime.now());
            usuariosRepository.save(usuario);
        }
    }

    @Override
    public List<Usuarios> listarActivos() {
        return usuariosRepository.findAllActivos();
    }

    @Override
    public List<Usuarios> listarActivosConPersona() {
        return usuariosRepository.findAllActivosConPersona();
    }

    @Override
    public List<Usuarios> listarPorEstado(Boolean estado) {
        return usuariosRepository.findByEstado(estado);
    }

    @Transactional
    @Override
    public Usuarios activar(Integer id) {
        Usuarios usuario = findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuario.setUsuaEstado(true);
        return usuariosRepository.save(usuario);
    }

    @Transactional
    @Override
    public Usuarios desactivar(Integer id) {
        Usuarios usuario = findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuario.setUsuaEstado(false);
        return usuariosRepository.save(usuario);
    }
}
