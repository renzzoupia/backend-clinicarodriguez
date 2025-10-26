package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Roles;
import com.clinicarodriguez.clinicarodriguez.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = {"http://localhost"})
public class RolesController {

    @Autowired
    private RolesService rolesService;

    // GET: Listar todos los roles
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        HashMap<String, Object> result = new HashMap<>();
        List<Roles> roles = rolesService.listarTodos();
        result.put("success", true);
        result.put("message", "Lista de roles");
        result.put("data", roles);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET: Buscar rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Roles> role = rolesService.buscarPorId(id);
        
        if (role.isPresent()) {
            result.put("success", true);
            result.put("message", "Rol encontrado");
            result.put("data", role.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "Rol no encontrado");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // GET: Buscar rol por nombre
    @GetMapping("/nombre/{roleName}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String roleName) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Roles> role = rolesService.buscarPorNombre(roleName);
        
        if (role.isPresent()) {
            result.put("success", true);
            result.put("message", "Rol encontrado");
            result.put("data", role.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "Rol no encontrado");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // POST: Crear nuevo rol
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Roles role) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            Roles nuevoRole = rolesService.guardar(role);
            result.put("success", true);
            result.put("message", "Rol creado correctamente");
            result.put("data", nuevoRole);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    // PUT: Actualizar rol
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Roles role) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            Roles roleActualizado = rolesService.actualizar(id, role);
            result.put("success", true);
            result.put("message", "Rol actualizado correctamente");
            result.put("data", roleActualizado);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Eliminar rol
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            rolesService.eliminar(id);
            result.put("success", true);
            result.put("message", "Rol eliminado correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // GET: Verificar si existe un rol por nombre
    @GetMapping("/existe/{roleName}")
    public ResponseEntity<?> existePorNombre(@PathVariable String roleName) {
        HashMap<String, Object> result = new HashMap<>();
        boolean existe = rolesService.existePorNombre(roleName);
        result.put("success", true);
        result.put("existe", existe);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
