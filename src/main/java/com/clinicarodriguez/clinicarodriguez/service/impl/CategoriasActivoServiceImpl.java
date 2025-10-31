package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.CategoriasActivo;
import com.clinicarodriguez.clinicarodriguez.repository.CategoriasActivoRepository;
import com.clinicarodriguez.clinicarodriguez.service.CategoriasActivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriasActivoServiceImpl implements CategoriasActivoService {

    @Autowired
    private CategoriasActivoRepository categoriasActivoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriasActivo> findAll() {
        return categoriasActivoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriasActivo> findById(Integer id) {
        return categoriasActivoRepository.findById(id);
    }

    @Override
    @Transactional
    public CategoriasActivo save(CategoriasActivo categoriasActivo) {
        return categoriasActivoRepository.save(categoriasActivo);
    }

    @Override
    @Transactional
    public CategoriasActivo update(Integer id, CategoriasActivo categoriasActivo) {
        Optional<CategoriasActivo> categoriaExistente = categoriasActivoRepository.findById(id);
        if (categoriaExistente.isPresent()) {
            CategoriasActivo categoriaActualizada = categoriaExistente.get();
            categoriaActualizada.setCaacNombreCategoria(categoriasActivo.getCaacNombreCategoria());
            categoriaActualizada.setCaacDescripcion(categoriasActivo.getCaacDescripcion());
            categoriaActualizada.setCaacEstado(categoriasActivo.getCaacEstado());
            return categoriasActivoRepository.save(categoriaActualizada);
        }
        throw new RuntimeException("Categoría de activo no encontrada con ID: " + id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        categoriasActivoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriasActivo> findByNombreCategoria(String nombreCategoria) {
        return categoriasActivoRepository.findByCaacNombreCategoria(nombreCategoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriasActivo> findByEstado(Integer estado) {
        return categoriasActivoRepository.findByCaacEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNombreCategoria(String nombreCategoria) {
        return categoriasActivoRepository.existsByCaacNombreCategoria(nombreCategoria);
    }
}
