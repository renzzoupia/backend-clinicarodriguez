/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicosRepository extends JpaRepository<Medicos, Long> {
    
    // Buscar médico por ID de usuario
    @Query("SELECT m FROM Medicos m WHERE m.usuario.usuaId = :usuarioId")
    Optional<Medicos> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}
