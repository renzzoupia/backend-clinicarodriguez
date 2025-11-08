package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Areas;
import com.clinicarodriguez.clinicarodriguez.repository.AreasRepository;
import com.clinicarodriguez.clinicarodriguez.service.AreasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AreasServiceImpl implements AreasService {

    @Autowired
    private AreasRepository areasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Areas> listarTodas() {
        return areasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Areas> buscarPorId(Integer id) {
        return areasRepository.findById(id);
    }

    @Override
    @Transactional
    public Areas guardar(Areas area) {
        return areasRepository.save(area);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        areasRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Areas> listarAreasRaiz() {
        return areasRepository.findAreasRaiz();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Areas> listarSubAreasPorPadre(Integer areaPadreId) {
        return areasRepository.findSubAreasByPadreId(areaPadreId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Areas> buscarPorNombre(String nombre) {
        return areasRepository.findByNombreContaining(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean tieneSubAreas(Integer areaId) {
        return areasRepository.tieneSubAreas(areaId);
    }
}
