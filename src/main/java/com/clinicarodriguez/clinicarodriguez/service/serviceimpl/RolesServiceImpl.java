package com.clinicarodriguez.clinicarodriguez.service.serviceimpl;

import com.clinicarodriguez.clinicarodriguez.model.Roles;
import com.clinicarodriguez.clinicarodriguez.repository.RolesRepository;
import com.clinicarodriguez.clinicarodriguez.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public List<Roles> listarTodos() {
        return rolesRepository.findAll();
    }

    @Override
    public Optional<Roles> buscarPorId(Long id) {
        return rolesRepository.findById(id);
    }

    @Override
    public Optional<Roles> buscarPorNombre(String roleName) {
        return rolesRepository.findByRoleName(roleName);
    }

    @Override
    public Roles guardar(Roles role) {
        // Validar que no exista un rol con el mismo nombre
        if (rolesRepository.existsByRoleName(role.getRoleName())) {
            throw new RuntimeException("Ya existe un rol con el nombre: " + role.getRoleName());
        }
        return rolesRepository.save(role);
    }

    @Override
    public Roles actualizar(Long id, Roles role) {
        if (!rolesRepository.existsById(id)) {
            throw new RuntimeException("Rol con ID " + id + " no encontrado");
        }
        
        // Verificar que el nombre no esté siendo usado por otro rol
        Optional<Roles> existingRole = rolesRepository.findByRoleName(role.getRoleName());
        if (existingRole.isPresent() && !existingRole.get().getRoleId().equals(id)) {
            throw new RuntimeException("Ya existe otro rol con el nombre: " + role.getRoleName());
        }
        
        role.setRoleId(id);
        return rolesRepository.save(role);
    }

    @Override
    public void eliminar(Long id) {
        if (!rolesRepository.existsById(id)) {
            throw new RuntimeException("Rol con ID " + id + " no encontrado");
        }
        rolesRepository.deleteById(id);
    }

    @Override
    public boolean existePorNombre(String roleName) {
        return rolesRepository.existsByRoleName(roleName);
    }
}
