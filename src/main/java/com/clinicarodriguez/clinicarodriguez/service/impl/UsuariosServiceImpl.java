package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.repository.UsuariosRepository;
import com.clinicarodriguez.clinicarodriguez.service.UsuariosService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariosServiceImpl implements UsuariosService {
    
    @Autowired
    private UsuariosRepository usuariosRepository;

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
}

