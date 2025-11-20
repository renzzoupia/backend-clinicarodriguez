package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.RecetaDetalle;
import com.clinicarodriguez.clinicarodriguez.repository.RecetaDetalleRepository;
import com.clinicarodriguez.clinicarodriguez.service.RecetaDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaDetalleServiceImpl implements RecetaDetalleService {

    @Autowired
    private RecetaDetalleRepository recetaDetalleRepository;

    @Override
    public List<RecetaDetalle> findAll() {
        return recetaDetalleRepository.findAll();
    }

    @Override
    public Optional<RecetaDetalle> findById(Integer id) {
        return recetaDetalleRepository.findById(id);
    }

    @Override
    public RecetaDetalle save(RecetaDetalle recetaDetalle) {
        return recetaDetalleRepository.save(recetaDetalle);
    }

    @Override
    public void deleteById(Integer id) {
        recetaDetalleRepository.deleteById(id);
    }

    @Override
    public List<RecetaDetalle> findByRecetaId(Integer recetaId) {
        return recetaDetalleRepository.findByRecetaId(recetaId);
    }

    @Override
    public List<RecetaDetalle> findByMedicamento(String medicamento) {
        return recetaDetalleRepository.findByMedicamento(medicamento);
    }
}
