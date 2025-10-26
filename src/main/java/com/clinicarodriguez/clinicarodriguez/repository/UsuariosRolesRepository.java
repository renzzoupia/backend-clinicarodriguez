package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.UsuariosRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRolesRepository extends JpaRepository<UsuariosRoles, Long> {
    
    // Buscar todos los roles de un usuario
    //@Query("SELECT ur FROM UsuariosRoles ur JOIN FETCH ur.role WHERE ur.usuario.usuaId = :usuarioId")
    @Query("SELECT ur FROM UsuariosRoles ur WHERE ur.usuario.usuaId = :usuarioId")
    List<UsuariosRoles> findByUsuarioId(@Param("usuarioId") Long usuarioId);
    
    // Buscar todos los usuarios con un rol específico
    @Query("SELECT ur FROM UsuariosRoles ur WHERE ur.role.roleId = :roleId")
    List<UsuariosRoles> findByRoleId(@Param("roleId") Long roleId);
    
    // Buscar una relación específica usuario-rol
    @Query("SELECT ur FROM UsuariosRoles ur WHERE ur.usuario.usuaId = :usuarioId AND ur.role.roleId = :roleId")
    Optional<UsuariosRoles> findByUsuarioIdAndRoleId(@Param("usuarioId") Long usuarioId, @Param("roleId") Long roleId);
    
    // Verificar si un usuario ya tiene un rol específico
    @Query("SELECT COUNT(ur) > 0 FROM UsuariosRoles ur WHERE ur.usuario.usuaId = :usuarioId AND ur.role.roleId = :roleId")
    boolean existsByUsuarioIdAndRoleId(@Param("usuarioId") Long usuarioId, @Param("roleId") Long roleId);
    
    // Eliminar todos los roles de un usuario
    @Query("DELETE FROM UsuariosRoles ur WHERE ur.usuario.usuaId = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);
}
