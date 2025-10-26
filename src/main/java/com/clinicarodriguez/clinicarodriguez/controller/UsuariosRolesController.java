package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.UsuariosRoles;
import com.clinicarodriguez.clinicarodriguez.service.UsuariosRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios-roles")
@CrossOrigin(origins = {"http://localhost"})
public class UsuariosRolesController {

    @Autowired
    private UsuariosRolesService usuariosRolesService;

    // GET: Listar todas las relaciones usuario-rol
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        HashMap<String, Object> result = new HashMap<>();
        List<UsuariosRoles> usuariosRoles = usuariosRolesService.listarTodos();
        result.put("success", true);
        result.put("message", "Lista de asignaciones usuario-rol");
        result.put("data", usuariosRoles);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET: Obtener todos los roles de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerRolesDeUsuario(@PathVariable Long usuarioId) {
        HashMap<String, Object> result = new HashMap<>();
        List<UsuariosRoles> roles = usuariosRolesService.obtenerRolesDeUsuario(usuarioId);
        result.put("success", true);
        result.put("message", "Roles del usuario");
        result.put("data", roles);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET: Obtener todos los usuarios con un rol específico
    @GetMapping("/rol/{roleId}")
    public ResponseEntity<?> obtenerUsuariosConRol(@PathVariable Long roleId) {
        HashMap<String, Object> result = new HashMap<>();
        List<UsuariosRoles> usuarios = usuariosRolesService.obtenerUsuariosConRol(roleId);
        result.put("success", true);
        result.put("message", "Usuarios con el rol especificado");
        result.put("data", usuarios);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST: Asignar un rol a un usuario
    @PostMapping("/asignar")
    public ResponseEntity<?> asignarRol(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("roleId") Long roleId) {
        
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            UsuariosRoles usuarioRole = usuariosRolesService.asignarRol(usuarioId, roleId);
            result.put("success", true);
            result.put("message", "Rol asignado correctamente al usuario");
            result.put("data", usuarioRole);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE: Quitar un rol de un usuario
    @DeleteMapping("/quitar")
    public ResponseEntity<?> quitarRol(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("roleId") Long roleId) {
        
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            usuariosRolesService.quitarRol(usuarioId, roleId);
            result.put("success", true);
            result.put("message", "Rol removido correctamente del usuario");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Quitar todos los roles de un usuario
    @DeleteMapping("/usuario/{usuarioId}/todos")
    public ResponseEntity<?> quitarTodosLosRoles(@PathVariable Long usuarioId) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            usuariosRolesService.quitarTodosLosRolesDeUsuario(usuarioId);
            result.put("success", true);
            result.put("message", "Todos los roles del usuario han sido removidos");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET: Verificar si un usuario tiene un rol específico
    @GetMapping("/verificar")
    public ResponseEntity<?> verificarRol(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("roleId") Long roleId) {
        
        HashMap<String, Object> result = new HashMap<>();
        boolean tieneRol = usuariosRolesService.usuarioTieneRol(usuarioId, roleId);
        result.put("success", true);
        result.put("tieneRol", tieneRol);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
