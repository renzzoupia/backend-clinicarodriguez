package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.DiaUsuario;
import com.clinicarodriguez.clinicarodriguez.repository.DiaUsuarioRepository;
import com.clinicarodriguez.clinicarodriguez.service.DiaUsuarioService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaUsuarioServiceImpl implements DiaUsuarioService {
    
    @Autowired
    private DiaUsuarioRepository diaUsuarioRepository;

    @Override
    public List<DiaUsuario> findAll() {
        return diaUsuarioRepository.findAll();
    }

    @Override
    public DiaUsuario findById(Long id) {
        return diaUsuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public DiaUsuario save(DiaUsuario diaUsuario) {
        return diaUsuarioRepository.save(diaUsuario);
    }

    @Transactional
    @Override
    public void delete(DiaUsuario diaUsuario) {
        diaUsuarioRepository.delete(diaUsuario);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        diaUsuarioRepository.deleteById(id);
    }

    @Override
    public List<DiaUsuario> findByUsuarioId(Long usuarioId) {
        return diaUsuarioRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<DiaUsuario> findByDiaId(Integer diaId) {
        return diaUsuarioRepository.findByDiaId(diaId);
    }

    @Override
    public List<DiaUsuario> findByUsuarioIdAndEstadoActivo(Long usuarioId) {
        return diaUsuarioRepository.findByUsuarioIdAndEstadoActivo(usuarioId);
    }
}
