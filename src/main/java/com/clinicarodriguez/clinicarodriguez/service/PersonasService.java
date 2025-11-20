package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.model.Personas.TipoDocumento;

import java.util.List;
import java.util.Optional;

public interface PersonasService {

    // CRUD básico
    List<Personas> listarTodas();
    Optional<Personas> buscarPorId(Integer id);
    Personas guardar(Personas persona);
    void eliminar(Integer id);
    Personas actualizar(Integer id, Personas persona);

    // Métodos de búsqueda específicos
    Optional<Personas> buscarPorTipoDocYNroDoc(TipoDocumento tipoDoc, String nroDoc);
    boolean existePorTipoDocYNroDoc(TipoDocumento tipoDoc, String nroDoc);
    Optional<Personas> buscarPorEmail(String email);
    boolean existePorEmail(String email);
    List<Personas> buscarPorNroDoc(String nroDoc);
    List<Personas> buscarPorNombre(String nombre);
    List<Personas> buscarPorTelefono(String telefono);
    Personas findById(Integer id);

    // Métodos por estado
    List<Personas> listarActivos();
    List<Personas> listarPorEstado(Boolean estado);
    long contarActivos();

    // Métodos para personas sin perfiles
    List<Personas> listarPersonasSinUsuario();
    List<Personas> listarPersonasSinPaciente();
    List<Personas> listarPersonasSinMedico();

    // Activar/Desactivar
    Personas activar(Integer id);
    Personas desactivar(Integer id);
}
