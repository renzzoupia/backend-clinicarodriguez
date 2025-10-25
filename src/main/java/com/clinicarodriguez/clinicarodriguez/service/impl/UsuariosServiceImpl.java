package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.repository.UsuariosRepository;
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
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public List<Usuarios> findAll() {
        return (List<Usuarios>) usuariosRepository.findAll();
    }

    @Override
    public Usuarios findById(Long id) {
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
    public void deleteById(Long id) {
        usuariosRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Usuarios registrarUsuario(Usuarios usuario) {
        // Encriptar la contraseña antes de guardar
        String claveEncriptada = passwordEncoder.encode(usuario.getUsuaClave());
        usuario.setUsuaClave(claveEncriptada);
        
        if (usuario.getUsuaEsActivo() == null) {
            usuario.setUsuaEsActivo(true);
        }
        
        return usuariosRepository.save(usuario);
    }

    @Override
    public Optional<Usuarios> findByUsername(String username) {
        return usuariosRepository.findByUsuaUsername(username);
    }

    @Override
    public Optional<Usuarios> findByEmail(String email) {
        return usuariosRepository.findByUsuaEmail(email);
    }

    @Override
    public boolean validarCredenciales(String username, String passwordTextoPlano) {
        Optional<Usuarios> usuarioOpt = usuariosRepository.findByUsuaUsername(username);
        
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        
        Usuarios usuario = usuarioOpt.get();
        
        // Verificar que el usuario esté activo
        if (!usuario.getUsuaEsActivo()) {
            return false;
        }
        
        // Comparar la contraseña en texto plano con la encriptada
        return passwordEncoder.matches(passwordTextoPlano, usuario.getUsuaClave());
    }

    @Override
    public boolean existsByUsername(String username) {
        return usuariosRepository.existsByUsuaUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuariosRepository.existsByUsuaEmail(email);
    }

    @Transactional
    @Override
    public void actualizarUltimaSesion(Long usuarioId) {
        Optional<Usuarios> usuarioOpt = usuariosRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            usuario.setUsuaUltimaSesion(LocalDateTime.now());
            usuariosRepository.save(usuario);
        }
    }
}

