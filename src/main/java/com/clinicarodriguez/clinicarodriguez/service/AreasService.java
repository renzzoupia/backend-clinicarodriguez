package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Areas;

import java.util.List;
import java.util.Optional;

public interface AreasService {

    // CRUD básico
    List<Areas> listarTodas();
    Optional<Areas> buscarPorId(Integer id);
    Areas guardar(Areas area);
    void eliminar(Integer id);

    // Métodos específicos para estructura jerárquica
    List<Areas> listarAreasRaiz();
    List<Areas> listarSubAreasPorPadre(Integer areaPadreId);
    List<Areas> buscarPorNombre(String nombre);
    boolean tieneSubAreas(Integer areaId);
}
