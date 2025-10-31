package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.ActivosTecnologicos;
import com.clinicarodriguez.clinicarodriguez.model.CategoriasActivo;
import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.repository.ActivosTecnologicosRepository;
import com.clinicarodriguez.clinicarodriguez.repository.CategoriasActivoRepository;
import com.clinicarodriguez.clinicarodriguez.repository.UsuariosRepository;
import com.clinicarodriguez.clinicarodriguez.service.ActivosTecnologicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActivosTecnologicosServiceImpl implements ActivosTecnologicosService {

    @Autowired
    private ActivosTecnologicosRepository activosTecnologicosRepository;

    @Autowired
    private CategoriasActivoRepository categoriasActivoRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ActivosTecnologicos> findAll() {
        return activosTecnologicosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActivosTecnologicos> findById(Integer id) {
        return activosTecnologicosRepository.findById(id);
    }

    @Override
    @Transactional
    public ActivosTecnologicos save(ActivosTecnologicos activosTecnologicos) {
        return activosTecnologicosRepository.save(activosTecnologicos);
    }

    @Override
    @Transactional
    public ActivosTecnologicos update(Integer id, ActivosTecnologicos activosTecnologicos) {
        Optional<ActivosTecnologicos> activoExistente = activosTecnologicosRepository.findById(id);
        if (activoExistente.isPresent()) {
            ActivosTecnologicos activoActualizado = activoExistente.get();
            activoActualizado.setActeCodigoActivo(activosTecnologicos.getActeCodigoActivo());
            activoActualizado.setActeNombreEquipo(activosTecnologicos.getActeNombreEquipo());
            activoActualizado.setCategoria(activosTecnologicos.getCategoria());
            activoActualizado.setActeMarca(activosTecnologicos.getActeMarca());
            activoActualizado.setActeModelo(activosTecnologicos.getActeModelo());
            activoActualizado.setActeNumeroSerie(activosTecnologicos.getActeNumeroSerie());
            activoActualizado.setActeFechaCompra(activosTecnologicos.getActeFechaCompra());
            activoActualizado.setActeEstado(activosTecnologicos.getActeEstado());
            activoActualizado.setActeUbicacion(activosTecnologicos.getActeUbicacion());
            activoActualizado.setUsuario(activosTecnologicos.getUsuario());
            activoActualizado.setActeVidaUtilAnios(activosTecnologicos.getActeVidaUtilAnios());
            activoActualizado.setActeFechaBaja(activosTecnologicos.getActeFechaBaja());
            activoActualizado.setActeObservaciones(activosTecnologicos.getActeObservaciones());
            return activosTecnologicosRepository.save(activoActualizado);
        }
        throw new RuntimeException("Activo tecnológico no encontrado con ID: " + id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        activosTecnologicosRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActivosTecnologicos> findByCodigoActivo(String codigoActivo) {
        return activosTecnologicosRepository.findByActeCodigoActivo(codigoActivo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivosTecnologicos> findByCategoria(Integer categoriaId) {
        Optional<CategoriasActivo> categoria = categoriasActivoRepository.findById(categoriaId);
        if (categoria.isPresent()) {
            return activosTecnologicosRepository.findByCategoria(categoria.get());
        }
        throw new RuntimeException("Categoría no encontrada con ID: " + categoriaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivosTecnologicos> findByEstado(String estado) {
        return activosTecnologicosRepository.findByActeEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivosTecnologicos> findByUbicacion(String ubicacion) {
        return activosTecnologicosRepository.findByActeUbicacion(ubicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivosTecnologicos> findByUsuarioId(Integer usuaId) {
        Optional<Usuarios> usuario = usuariosRepository.findById(Long.valueOf(usuaId));
        if (usuario.isPresent()) {
            return activosTecnologicosRepository.findByUsuario(usuario.get());
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + usuaId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCodigoActivo(String codigoActivo) {
        return activosTecnologicosRepository.existsByActeCodigoActivo(codigoActivo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNumeroSerie(String numeroSerie) {
        return activosTecnologicosRepository.existsByActeNumeroSerie(numeroSerie);
    }
}
