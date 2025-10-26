package com.clinicarodriguez.clinicarodriguez.service.serviceimpl;

import com.clinicarodriguez.clinicarodriguez.model.Roles;
import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.model.UsuariosRoles;
import com.clinicarodriguez.clinicarodriguez.repository.RolesRepository;
import com.clinicarodriguez.clinicarodriguez.repository.UsuariosRepository;
import com.clinicarodriguez.clinicarodriguez.repository.UsuariosRolesRepository;
import com.clinicarodriguez.clinicarodriguez.service.UsuariosRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosRolesServiceImpl implements UsuariosRolesService {

    @Autowired
    private UsuariosRolesRepository usuariosRolesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public List<UsuariosRoles> listarTodos() {
        return usuariosRolesRepository.findAll();
    }

    @Override
    public Optional<UsuariosRoles> buscarPorId(Long id) {
        return usuariosRolesRepository.findById(id);
    }

    @Override
    public List<UsuariosRoles> obtenerRolesDeUsuario(Long usuarioId) {
        return usuariosRolesRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<UsuariosRoles> obtenerUsuariosConRol(Long roleId) {
        return usuariosRolesRepository.findByRoleId(roleId);
    }

    @Override
    @Transactional
    public UsuariosRoles asignarRol(Long usuarioId, Long roleId) {
        // Validar que el usuario existe
        Optional<Usuarios> usuario = usuariosRepository.findById(usuarioId);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado");
        }

        // Validar que el rol existe
        Optional<Roles> role = rolesRepository.findById(roleId);
        if (role.isEmpty()) {
            throw new RuntimeException("Rol con ID " + roleId + " no encontrado");
        }

        // Verificar si el usuario ya tiene ese rol
        if (usuariosRolesRepository.existsByUsuarioIdAndRoleId(usuarioId, roleId)) {
            throw new RuntimeException("El usuario ya tiene asignado este rol");
        }

        // Crear la relación
        UsuariosRoles usuarioRole = new UsuariosRoles();
        usuarioRole.setUsuario(usuario.get());
        usuarioRole.setRole(role.get());

        return usuariosRolesRepository.save(usuarioRole);
    }

    @Override
    @Transactional
    public void quitarRol(Long usuarioId, Long roleId) {
        Optional<UsuariosRoles> usuarioRole = usuariosRolesRepository.findByUsuarioIdAndRoleId(usuarioId, roleId);
        
        if (usuarioRole.isEmpty()) {
            throw new RuntimeException("El usuario no tiene asignado este rol");
        }

        usuariosRolesRepository.delete(usuarioRole.get());
    }

    @Override
    @Transactional
    public void quitarTodosLosRolesDeUsuario(Long usuarioId) {
        usuariosRolesRepository.deleteByUsuarioId(usuarioId);
    }

    @Override
    public boolean usuarioTieneRol(Long usuarioId, Long roleId) {
        return usuariosRolesRepository.existsByUsuarioIdAndRoleId(usuarioId, roleId);
    }
}
