package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Receta;
import com.clinicarodriguez.clinicarodriguez.repository.RecetaRepository;
import com.clinicarodriguez.clinicarodriguez.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public List<Receta> findAll() {
        return recetaRepository.findAll();
    }

    @Override
    public Optional<Receta> findById(Integer id) {
        return recetaRepository.findById(id);
    }

    @Override
    public Receta save(Receta receta) {
        return recetaRepository.save(receta);
    }

    @Override
    public void deleteById(Integer id) {
        recetaRepository.deleteById(id);
    }

    @Override
    public List<Receta> findByEpisodioId(Integer episodioId) {
        return recetaRepository.findByEpisodioId(episodioId);
    }

    @Override
    public List<Receta> findByEstado(Boolean estado) {
        return recetaRepository.findByReceEstado(estado);
    }

    @Override
    public List<Receta> findByEpisodioIdAndEstado(Integer episodioId, Boolean estado) {
        return recetaRepository.findByEpisodioIdAndEstado(episodioId, estado);
    }
}
