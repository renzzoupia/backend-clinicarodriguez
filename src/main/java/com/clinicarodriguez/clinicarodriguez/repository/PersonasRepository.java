package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.model.Personas.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonasRepository extends JpaRepository<Personas, Integer> {

    // Buscar por tipo de documento y número de documento (combinación única)
    @Query("SELECT p FROM Personas p WHERE p.persTipoDoc = :tipoDoc AND p.persNroDoc = :nroDoc")
    Optional<Personas> findByTipoDocAndNroDoc(@Param("tipoDoc") TipoDocumento tipoDoc, @Param("nroDoc") String nroDoc);

    // Verificar si existe persona con tipo y número de documento
    @Query("SELECT COUNT(p) > 0 FROM Personas p WHERE p.persTipoDoc = :tipoDoc AND p.persNroDoc = :nroDoc")
    boolean existsByTipoDocAndNroDoc(@Param("tipoDoc") TipoDocumento tipoDoc, @Param("nroDoc") String nroDoc);

    // Buscar por email
    @Query("SELECT p FROM Personas p WHERE LOWER(p.persEmail) = LOWER(:email)")
    Optional<Personas> findByEmail(@Param("email") String email);

    // Verificar si existe persona con email
    @Query("SELECT COUNT(p) > 0 FROM Personas p WHERE LOWER(p.persEmail) = LOWER(:email)")
    boolean existsByEmail(@Param("email") String email);

    // Buscar por número de documento (sin importar tipo)
    @Query("SELECT p FROM Personas p WHERE p.persNroDoc = :nroDoc")
    List<Personas> findByNroDoc(@Param("nroDoc") String nroDoc);

    // Buscar por nombre completo (búsqueda parcial)
    @Query("SELECT p FROM Personas p WHERE LOWER(p.persNombrecompleto) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Personas> findByNombreContaining(@Param("nombre") String nombre);

    // Listar personas activas
    @Query("SELECT p FROM Personas p WHERE p.persEsActivo = true ORDER BY p.persNombrecompleto")
    List<Personas> findAllActivos();

    // Listar personas por estado
    @Query("SELECT p FROM Personas p WHERE p.persEsActivo = :estado ORDER BY p.persNombrecompleto")
    List<Personas> findByEstado(@Param("estado") Boolean estado);

    // Buscar personas sin perfil de usuario
    @Query("SELECT p FROM Personas p WHERE p.usuario IS NULL AND p.persEsActivo = true")
    List<Personas> findPersonasSinUsuario();

    // Buscar personas sin perfil de paciente
    @Query("SELECT p FROM Personas p WHERE p.paciente IS NULL AND p.persEsActivo = true")
    List<Personas> findPersonasSinPaciente();

    // Buscar personas sin perfil de médico
    @Query("SELECT p FROM Personas p WHERE p.medico IS NULL AND p.persEsActivo = true")
    List<Personas> findPersonasSinMedico();

    // Buscar por teléfono
    @Query("SELECT p FROM Personas p WHERE p.persTelefono = :telefono")
    List<Personas> findByTelefono(@Param("telefono") String telefono);

    // Contar personas activas
    @Query("SELECT COUNT(p) FROM Personas p WHERE p.persEsActivo = true")
    long countActivos();
}
