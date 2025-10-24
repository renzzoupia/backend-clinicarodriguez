package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.MedicosEspecialidades;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicosEspecialidadesRepository extends JpaRepository<MedicosEspecialidades, Long> {
    
    // Buscar todas las especialidades de un médico
    @Query("SELECT me FROM MedicosEspecialidades me WHERE me.medico.mediId = :medicoId")
    List<MedicosEspecialidades> findByMedicoId(@Param("medicoId") Long medicoId);
    
    // Buscar todos los médicos de una especialidad
    @Query("SELECT me FROM MedicosEspecialidades me WHERE me.especialidad.espeId = :especialidadId")
    List<MedicosEspecialidades> findByEspecialidadId(@Param("especialidadId") Long especialidadId);
    
    // Verificar si existe una relación entre médico y especialidad
    @Query("SELECT COUNT(me) > 0 FROM MedicosEspecialidades me WHERE me.medico.mediId = :medicoId AND me.especialidad.espeId = :especialidadId")
    boolean existsByMedicoAndEspecialidad(@Param("medicoId") Long medicoId, @Param("especialidadId") Long especialidadId);
}

