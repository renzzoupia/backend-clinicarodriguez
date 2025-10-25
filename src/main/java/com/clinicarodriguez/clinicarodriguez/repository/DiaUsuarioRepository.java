package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.DiaUsuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiaUsuarioRepository extends JpaRepository<DiaUsuario, Long> {
    
    // Buscar disponibilidad por usuario
    @Query("SELECT du FROM DiaUsuario du WHERE du.usuario.usuaId = :usuarioId")
    List<DiaUsuario> findByUsuarioId(@Param("usuarioId") Long usuarioId);
    
    // Buscar disponibilidad por día
    @Query("SELECT du FROM DiaUsuario du WHERE du.dia.diasId = :diaId")
    List<DiaUsuario> findByDiaId(@Param("diaId") Integer diaId);
    
    // Buscar disponibilidad activa de un usuario
    @Query("SELECT du FROM DiaUsuario du WHERE du.usuario.usuaId = :usuarioId AND du.diusEstado = 1")
    List<DiaUsuario> findByUsuarioIdAndEstadoActivo(@Param("usuarioId") Long usuarioId);
}
