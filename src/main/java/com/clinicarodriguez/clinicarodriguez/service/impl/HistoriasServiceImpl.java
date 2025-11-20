package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Historias;
import com.clinicarodriguez.clinicarodriguez.repository.HistoriasRepository;
import com.clinicarodriguez.clinicarodriguez.service.HistoriasService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoriasServiceImpl implements HistoriasService {
    
    @Autowired
    private HistoriasRepository historiasRepository;

    @Override
    public List<Historias> findAll() {
        return historiasRepository.findAll();
    }

    @Override
    public Historias findById(Integer id) {
        return historiasRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Historias save(Historias historia) {
        // Establecer fecha de registro si no está definida
        if (historia.getHistRegistrofecha() == null) {
            historia.setHistRegistrofecha(LocalDate.now());
        }
        
        return historiasRepository.save(historia);
    }

    @Transactional
    @Override
    public void delete(Historias historia) {
        historiasRepository.delete(historia);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        historiasRepository.deleteById(id);
    }

    @Override
    public List<Historias> findByPacienteId(Integer pacienteId) {
        return historiasRepository.findByPacienteId(pacienteId);
    }

    @Override
    public List<Historias> findByUsuarioId(Integer usuarioId) {
        return historiasRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public long countByPacienteId(Integer pacienteId) {
        return historiasRepository.countByPacienteId(pacienteId);
    }
}
