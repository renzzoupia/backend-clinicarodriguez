package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.DiasMedico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiasMedicoRepository extends JpaRepository<DiasMedico, Long> {
    
    // Buscar disponibilidad por médico
    @Query("SELECT dm FROM DiasMedico dm WHERE dm.medico.mediId = :medicoId")
    List<DiasMedico> findByMedicoId(@Param("medicoId") Long medicoId);
    
    // Buscar disponibilidad por día
    @Query("SELECT dm FROM DiasMedico dm WHERE dm.dia.diasId = :diaId")
    List<DiasMedico> findByDiaId(@Param("diaId") Integer diaId);
    
    // Buscar disponibilidad activa de un médico
    @Query("SELECT dm FROM DiasMedico dm WHERE dm.medico.mediId = :medicoId AND dm.dimeEstado = 1")
    List<DiasMedico> findByMedicoIdAndEstadoActivo(@Param("medicoId") Long medicoId);
}
