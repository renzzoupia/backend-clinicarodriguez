package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.dto.AuthResponse;
import com.clinicarodriguez.clinicarodriguez.dto.LoginRequest;
import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.model.UsuariosRoles;
import com.clinicarodriguez.clinicarodriguez.security.JwtTokenProvider;
import com.clinicarodriguez.clinicarodriguez.service.UsuariosService;
import com.clinicarodriguez.clinicarodriguez.service.UsuariosRolesService;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UsuariosService usuariosService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private UsuariosRolesService usuariosRolesService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Validar que los campos no estén vacíos
            if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
                result.put("success", false);
                result.put("message", "El username es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                result.put("success", false);
                result.put("message", "La contraseña es requerida");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            // Validar credenciales
            if (!usuariosService.validarCredenciales(loginRequest.getUsername(), loginRequest.getPassword())) {
                result.put("success", false);
                result.put("message", "Usuario o contraseña incorrectos");
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            }
            
            // Buscar usuario
            Optional<Usuarios> usuarioOpt = usuariosService.findByUsername(loginRequest.getUsername());
            
            if (usuarioOpt.isEmpty()) {
                result.put("success", false);
                result.put("message", "Usuario no encontrado");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            
            Usuarios usuario = usuarioOpt.get();
            
            // Actualizar última sesión
            usuariosService.actualizarUltimaSesion(usuario.getUsuaId());
            
            // Obtener roles del usuario
            List<UsuariosRoles> usuariosRoles = usuariosRolesService.obtenerRolesDeUsuario(usuario.getUsuaId());
            
            // Extraer nombres de roles
            List<String> rolesNombres = usuariosRoles.stream()
                    .map(ur -> ur.getRole().getRoleName())
                    .collect(Collectors.toList());
            
            // Usar el primer rol para el token, o "USER" por defecto
            String rolePrincipal = rolesNombres.isEmpty() ? "USER" : rolesNombres.get(0);
            
            // Generar token JWT
            String token = jwtTokenProvider.generateToken(
                usuario.getUsuaUsername(), 
                usuario.getUsuaId(),
                rolePrincipal
            );
            
            // Preparar respuesta
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(token);
            authResponse.setUsuario(usuario);
            
            result.put("success", true);
            result.put("message", "Login exitoso");
            result.put("data", authResponse);
            result.put("roles", rolesNombres);  // Agregar lista de roles
            result.put("usuariosRoles", usuariosRoles);  // Información completa de roles
            
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al procesar login: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuarios usuario) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Validaciones
            if (usuario.getUsuaUsername() == null || usuario.getUsuaUsername().isEmpty()) {
                result.put("success", false);
                result.put("message", "El username es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            if (usuario.getUsuaEmail() == null || usuario.getUsuaEmail().isEmpty()) {
                result.put("success", false);
                result.put("message", "El email es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            if (usuario.getUsuaClave() == null || usuario.getUsuaClave().isEmpty()) {
                result.put("success", false);
                result.put("message", "La contraseña es requerida");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            // Verificar si el username ya existe
            if (usuariosService.existsByUsername(usuario.getUsuaUsername())) {
                result.put("success", false);
                result.put("message", "El username ya está registrado");
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            }
            
            // Verificar si el email ya existe
            if (usuariosService.existsByEmail(usuario.getUsuaEmail())) {
                result.put("success", false);
                result.put("message", "El email ya está registrado");
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            }
            
            // Registrar usuario (la contraseña se encripta en el service)
            Usuarios usuarioGuardado = usuariosService.registrarUsuario(usuario);
            
            // Ocultar la contraseña en la respuesta
            usuarioGuardado.setUsuaClave(null);
            
            result.put("success", true);
            result.put("message", "Usuario registrado exitosamente");
            result.put("data", usuarioGuardado);
            
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al registrar usuario: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
