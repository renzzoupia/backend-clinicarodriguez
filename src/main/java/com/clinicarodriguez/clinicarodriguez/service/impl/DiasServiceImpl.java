package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Dias;
import com.clinicarodriguez.clinicarodriguez.model.Dias.DiaSemana;
import com.clinicarodriguez.clinicarodriguez.repository.DiasRepository;
import com.clinicarodriguez.clinicarodriguez.service.DiasService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiasServiceImpl implements DiasService {
    
    @Autowired
    private DiasRepository diasRepository;

    @Override
    public List<Dias> findAll() {
        return diasRepository.findAll();
    }

    @Override
    public Dias findById(Integer id) {
        return diasRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Dias save(Dias dia) {
        return diasRepository.save(dia);
    }

    @Transactional
    @Override
    public void delete(Dias dia) {
        diasRepository.delete(dia);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        diasRepository.deleteById(id);
    }

    @Override
    public Optional<Dias> findByDia(DiaSemana dia) {
        return diasRepository.findByDia(dia);
    }

    @Transactional
    @Override
    public void inicializarDias() {
        // Crear los 7 días de la semana si no existen
        for (DiaSemana diaSemana : DiaSemana.values()) {
            if (!diasRepository.existsByDia(diaSemana)) {
                Dias dia = new Dias();
                dia.setDia(diaSemana);
                diasRepository.save(dia);
            }
        }
    }
}
