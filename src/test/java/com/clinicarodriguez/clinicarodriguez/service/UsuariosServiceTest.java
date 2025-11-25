package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.repository.UsuariosRepository;
import com.clinicarodriguez.clinicarodriguez.service.impl.UsuariosServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el módulo de usuarios y seguridad
 * Cubre los escenarios de autenticación, registro y control de acceso
 */
@DisplayName("Pruebas del Módulo de Usuarios y Seguridad")
class UsuariosServiceTest {

    @Mock
    private UsuariosRepository usuariosRepository;

    @Mock
    private PersonasService personasService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuariosServiceImpl usuariosService;

    private Usuarios usuarioTest;
    private Personas personaTest;
    private String passwordTextoPlano;
    private String passwordEncriptado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        passwordTextoPlano = "password123";
        passwordEncriptado = "$2a$10$abcdefghijklmnopqrstuv"; // Simulación de BCrypt

        // Configurar Persona de prueba
        personaTest = new Personas();
        personaTest.setPersId(1);
        personaTest.setPersNombrecompleto("Juan Pérez");
        personaTest.setPersEmail("juan.perez@clinica.com");
        personaTest.setPersTipoDoc(Personas.TipoDocumento.DNI);
        personaTest.setPersNroDoc("12345678");
        personaTest.setPersEsActivo(true);

        // Configurar Usuario de prueba
        usuarioTest = new Usuarios();
        usuarioTest.setUsuaId(1);
        usuarioTest.setPersona(personaTest);
        usuarioTest.setUsuaUsername("jperez");
        usuarioTest.setUsuaClave(passwordEncriptado);
        usuarioTest.setUsuaEstado(true);
        usuarioTest.setUsuaUltimaSesion(LocalDateTime.now());
    }

    @Test
    @DisplayName("1. Login - Credenciales Correctas: Debe retornar true y validar acceso")
    void login_CredencialesCorrectas_RetornaToken() {
        // Arrange: Usuario existe y contraseña es correcta
        when(usuariosRepository.findByUsuaUsername("jperez"))
                .thenReturn(Optional.of(usuarioTest));
        
        when(passwordEncoder.matches(passwordTextoPlano, passwordEncriptado))
                .thenReturn(true); // La contraseña coincide

        // Act: Validar credenciales
        boolean credencialesValidas = usuariosService.validarCredenciales("jperez", passwordTextoPlano);

        // Assert: Verificar que la autenticación fue exitosa
        assertTrue(credencialesValidas, 
                "Las credenciales correctas deben retornar true");
        
        // Verificar que se consultó el repositorio
        verify(usuariosRepository, times(1)).findByUsuaUsername("jperez");
        verify(passwordEncoder, times(1)).matches(passwordTextoPlano, passwordEncriptado);
    }

    @Test
    @DisplayName("2. Login - Usuario No Encontrado: Debe retornar false")
    void login_UsuarioNoEncontrado() {
        // Arrange: Usuario no existe en la base de datos
        when(usuariosRepository.findByUsuaUsername("usuarioInexistente"))
                .thenReturn(Optional.empty()); // No existe

        // Act: Intentar validar credenciales
        boolean credencialesValidas = usuariosService.validarCredenciales(
                "usuarioInexistente", 
                passwordTextoPlano
        );

        // Assert: Verificar que retorna false
        assertFalse(credencialesValidas, 
                "Debe retornar false cuando el usuario no existe");
        
        // Verificar que se consultó el repositorio
        verify(usuariosRepository, times(1)).findByUsuaUsername("usuarioInexistente");
        
        // Verificar que NO se intentó validar la contraseña (porque el usuario no existe)
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("3. Login - Password Incorrecto: Usuario existe pero contraseña es incorrecta")
    void login_PasswordIncorrecto() {
        // Arrange: Usuario existe pero contraseña no coincide
        when(usuariosRepository.findByUsuaUsername("jperez"))
                .thenReturn(Optional.of(usuarioTest));
        
        String passwordIncorrecta = "passwordIncorrecta123";
        when(passwordEncoder.matches(passwordIncorrecta, passwordEncriptado))
                .thenReturn(false); // La contraseña NO coincide

        // Act: Intentar validar con contraseña incorrecta
        boolean credencialesValidas = usuariosService.validarCredenciales(
                "jperez", 
                passwordIncorrecta
        );

        // Assert: Verificar que retorna false
        assertFalse(credencialesValidas, 
                "Debe retornar false cuando la contraseña es incorrecta");
        
        // Verificar que se consultó el repositorio y se validó la contraseña
        verify(usuariosRepository, times(1)).findByUsuaUsername("jperez");
        verify(passwordEncoder, times(1)).matches(passwordIncorrecta, passwordEncriptado);
    }

    @Test
    @DisplayName("4. Registrar Usuario - Email/Username Duplicado: Debe lanzar excepción")
    void registrarUsuario_EmailDuplicado() {
        // Arrange: Simular que el username ya existe
        when(usuariosRepository.existsByUsuaUsername("jperez"))
                .thenReturn(true); // Ya existe

        // Crear nuevo usuario con username duplicado
        Personas nuevaPersona = new Personas();
        nuevaPersona.setPersNombrecompleto("María García");
        nuevaPersona.setPersEmail("maria.garcia@clinica.com");
        nuevaPersona.setPersTipoDoc(Personas.TipoDocumento.DNI);
        nuevaPersona.setPersNroDoc("87654321");

        // Act & Assert: Verificar que lanza excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuariosService.crearUsuarioCompleto(
                    nuevaPersona,
                    "jperez", // Username duplicado
                    "nuevaPassword123"
            );
        }, "Debe lanzar RuntimeException cuando el username ya existe");

        // Verificar el mensaje de error
        assertTrue(exception.getMessage().contains("El username ya existe"),
                "El mensaje debe indicar que el username está duplicado");
        
        // Verificar que se validó la existencia del username
        verify(usuariosRepository, times(1)).existsByUsuaUsername("jperez");
        
        // Verificar que NO se guardó nada
        verify(usuariosRepository, never()).save(any(Usuarios.class));
        verify(personasService, never()).guardar(any(Personas.class));
    }

    @Test
    @DisplayName("5. Registrar Usuario - Email Único: Debe crear exitosamente")
    void registrarUsuario_EmailUnico_CreaExitosamente() {
        // Arrange: Username no existe
        when(usuariosRepository.existsByUsuaUsername("nuevousuario"))
                .thenReturn(false); // No existe
        
        // Documento tampoco existe
        when(personasService.existePorTipoDocYNroDoc(
                Personas.TipoDocumento.DNI, 
                "99999999"
        )).thenReturn(false);
        
        // Simulación de guardado de persona
        Personas nuevaPersona = new Personas();
        nuevaPersona.setPersNombrecompleto("Carlos López");
        nuevaPersona.setPersEmail("carlos.lopez@clinica.com");
        nuevaPersona.setPersTipoDoc(Personas.TipoDocumento.DNI);
        nuevaPersona.setPersNroDoc("99999999");
        
        Personas personaGuardada = new Personas();
        personaGuardada.setPersId(10); // Simulación de ID asignado
        personaGuardada.setPersNombrecompleto(nuevaPersona.getPersNombrecompleto());
        personaGuardada.setPersEmail(nuevaPersona.getPersEmail());
        
        when(personasService.guardar(any(Personas.class))).thenReturn(personaGuardada);
        
        // Simulación de encriptación
        when(passwordEncoder.encode("nuevaPassword123"))
                .thenReturn("$2a$10$newEncryptedPassword");
        
        // Simulación de guardado de usuario
        Usuarios usuarioEsperado = new Usuarios();
        usuarioEsperado.setUsuaId(10);
        usuarioEsperado.setPersona(personaGuardada);
        usuarioEsperado.setUsuaUsername("nuevousuario");
        usuarioEsperado.setUsuaClave("$2a$10$newEncryptedPassword");
        usuarioEsperado.setUsuaEstado(true);
        
        when(usuariosRepository.save(any(Usuarios.class))).thenReturn(usuarioEsperado);

        // Act: Crear usuario
        Usuarios usuarioCreado = usuariosService.crearUsuarioCompleto(
                nuevaPersona,
                "nuevousuario",
                "nuevaPassword123"
        );

        // Assert: Verificar que se creó correctamente
        assertNotNull(usuarioCreado, "El usuario creado no debe ser null");
        assertEquals("nuevousuario", usuarioCreado.getUsuaUsername(),
                "El username debe ser el esperado");
        assertTrue(usuarioCreado.getUsuaEstado(),
                "El usuario debe estar activo");
        assertEquals(10, usuarioCreado.getPersona().getPersId(),
                "La persona debe tener el ID asignado");
        
        // Verificar que se llamaron los métodos correctos
        verify(usuariosRepository, times(1)).existsByUsuaUsername("nuevousuario");
        verify(personasService, times(1)).existePorTipoDocYNroDoc(
                Personas.TipoDocumento.DNI, "99999999");
        verify(personasService, times(1)).guardar(any(Personas.class));
        verify(passwordEncoder, times(1)).encode("nuevaPassword123");
        verify(usuariosRepository, times(1)).save(any(Usuarios.class));
    }

    @Test
    @DisplayName("6. Login - Usuario Inactivo: Debe denegar acceso")
    void login_UsuarioInactivo_DenegaAcceso() {
        // Arrange: Usuario existe pero está inactivo
        usuarioTest.setUsuaEstado(false); // Usuario desactivado
        
        when(usuariosRepository.findByUsuaUsername("jperez"))
                .thenReturn(Optional.of(usuarioTest));

        // Act: Intentar validar credenciales
        boolean credencialesValidas = usuariosService.validarCredenciales(
                "jperez",
                passwordTextoPlano
        );

        // Assert: Verificar que retorna false
        assertFalse(credencialesValidas,
                "Debe retornar false cuando el usuario está inactivo");
        
        // Verificar que NO se validó la contraseña (porque el usuario está inactivo)
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("7. Registrar Usuario - Documento Duplicado: Debe lanzar excepción")
    void registrarUsuario_DocumentoDuplicado() {
        // Arrange: Username no existe pero documento SÍ
        when(usuariosRepository.existsByUsuaUsername("nuevousuario"))
                .thenReturn(false);
        
        when(personasService.existePorTipoDocYNroDoc(
                Personas.TipoDocumento.DNI,
                "12345678" // Documento que ya existe
        )).thenReturn(true); // Ya existe
        
        Personas personaDuplicada = new Personas();
        personaDuplicada.setPersNombrecompleto("Otro Nombre");
        personaDuplicada.setPersTipoDoc(Personas.TipoDocumento.DNI);
        personaDuplicada.setPersNroDoc("12345678"); // Documento duplicado

        // Act & Assert: Verificar que lanza excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuariosService.crearUsuarioCompleto(
                    personaDuplicada,
                    "nuevousuario",
                    "password123"
            );
        }, "Debe lanzar RuntimeException cuando el documento ya existe");

        assertTrue(exception.getMessage().contains("Ya existe una persona con ese documento"),
                "El mensaje debe indicar que el documento está duplicado");
        
        verify(usuariosRepository, never()).save(any(Usuarios.class));
    }

    @Test
    @DisplayName("8. Actualizar Última Sesión - Debe registrar fecha y hora")
    void actualizarUltimaSesion_RegistraFechaHora() {
        // Arrange: Usuario existe
        when(usuariosRepository.findById(1)).thenReturn(Optional.of(usuarioTest));
        when(usuariosRepository.save(any(Usuarios.class))).thenAnswer(invocation -> {
            Usuarios usuario = invocation.getArgument(0);
            return usuario;
        });

        // Act: Actualizar última sesión
        usuariosService.actualizarUltimaSesion(1);

        // Assert: Verificar que se guardó
        verify(usuariosRepository, times(1)).findById(1);
        verify(usuariosRepository, times(1)).save(argThat(usuario -> {
            // Verificar que la última sesión fue actualizada (debe ser reciente)
            LocalDateTime ultimaSesion = usuario.getUsuaUltimaSesion();
            return ultimaSesion != null && 
                   ultimaSesion.isAfter(LocalDateTime.now().minusMinutes(1));
        }));
    }

    @Test
    @DisplayName("9. Buscar Usuario por Username - Debe encontrar si existe")
    void buscarUsuario_PorUsername_EncuentraSiExiste() {
        // Arrange: Usuario existe
        when(usuariosRepository.findByUsuaUsername("jperez"))
                .thenReturn(Optional.of(usuarioTest));

        // Act: Buscar usuario
        Optional<Usuarios> usuarioEncontrado = usuariosService.findByUsername("jperez");

        // Assert: Verificar que se encontró
        assertTrue(usuarioEncontrado.isPresent(), "Debe encontrar el usuario");
        assertEquals("jperez", usuarioEncontrado.get().getUsuaUsername(),
                "El username debe coincidir");
        
        verify(usuariosRepository, times(1)).findByUsuaUsername("jperez");
    }

    @Test
    @DisplayName("10. Validar Encriptación de Contraseña - Debe usar BCrypt")
    void registrarUsuario_EncriptaPassword() {
        // Arrange: Configurar mocks para registro básico
        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setUsuaUsername("testuser");
        nuevoUsuario.setUsuaClave("plainPassword123"); // Contraseña en texto plano
        
        when(passwordEncoder.encode("plainPassword123"))
                .thenReturn("$2a$10$encryptedPasswordHash");
        
        when(usuariosRepository.save(any(Usuarios.class))).thenAnswer(invocation -> {
            Usuarios usuario = invocation.getArgument(0);
            usuario.setUsuaId(100);
            return usuario;
        });

        // Act: Registrar usuario
        Usuarios usuarioRegistrado = usuariosService.registrarUsuario(nuevoUsuario);

        // Assert: Verificar que la contraseña fue encriptada
        assertNotNull(usuarioRegistrado, "El usuario registrado no debe ser null");
        assertEquals("$2a$10$encryptedPasswordHash", usuarioRegistrado.getUsuaClave(),
                "La contraseña debe estar encriptada");
        assertNotEquals("plainPassword123", usuarioRegistrado.getUsuaClave(),
                "La contraseña NO debe estar en texto plano");
        
        verify(passwordEncoder, times(1)).encode("plainPassword123");
        verify(usuariosRepository, times(1)).save(any(Usuarios.class));
    }
}
