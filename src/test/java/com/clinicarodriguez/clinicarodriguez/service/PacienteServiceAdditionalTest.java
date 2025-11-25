package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.repository.PacienteRepository;
import com.clinicarodriguez.clinicarodriguez.service.impl.PacienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias adicionales para el módulo de gestión de pacientes
 * Cubre escenarios adicionales de registro, búsqueda, validación y activación/desactivación
 */
@DisplayName("Pruebas Adicionales del Módulo de Gestión de Pacientes")
class PacienteServiceAdditionalTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PersonasService personasService;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Paciente pacienteTest;
    private Personas personaTest;
    private Personas apoderadoTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar Persona de prueba
        personaTest = new Personas();
        personaTest.setPersId(1);
        personaTest.setPersNombrecompleto("María García López");
        personaTest.setPersTipoDoc(Personas.TipoDocumento.DNI);
        personaTest.setPersNroDoc("12345678");
        personaTest.setPersSexo(Personas.Sexo.FEMENINO);
        personaTest.setPersFecNacimiento(LocalDate.of(1990, 5, 15));
        personaTest.setPersEmail("maria.garcia@email.com");
        personaTest.setPersTelefono("987654321");
        personaTest.setPersEsActivo(true);

        // Configurar Apoderado de prueba
        apoderadoTest = new Personas();
        apoderadoTest.setPersId(2);
        apoderadoTest.setPersNombrecompleto("Juan García (Padre)");
        apoderadoTest.setPersTipoDoc(Personas.TipoDocumento.DNI);
        apoderadoTest.setPersNroDoc("87654321");
        apoderadoTest.setPersEsActivo(true);

        // Configurar Paciente de prueba
        pacienteTest = new Paciente();
        pacienteTest.setPaciId(1);
        pacienteTest.setPersona(personaTest);
        pacienteTest.setPaciEstado(true);
    }

    @Test
    @DisplayName("1. Registrar Paciente - Documento Único: Debe crear exitosamente")
    void registrarPaciente_DocumentoUnico_CreaExitosamente() {
        // Arrange: Documento no existe
        when(personasService.existePorTipoDocYNroDoc(
                Personas.TipoDocumento.DNI,
                "12345678"
        )).thenReturn(false);

        Personas personaGuardada = new Personas();
        personaGuardada.setPersId(10);
        personaGuardada.setPersNombrecompleto(personaTest.getPersNombrecompleto());
        personaGuardada.setPersNroDoc(personaTest.getPersNroDoc());

        when(personasService.guardar(any(Personas.class))).thenReturn(personaGuardada);

        Paciente pacienteEsperado = new Paciente();
        pacienteEsperado.setPaciId(10);
        pacienteEsperado.setPersona(personaGuardada);
        pacienteEsperado.setPaciEstado(true);

        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteEsperado);

        // Act: Crear paciente
        Paciente pacienteCreado = pacienteService.crearPacienteCompleto(personaTest);

        // Assert: Verificar creación exitosa
        assertNotNull(pacienteCreado, "El paciente creado no debe ser null");
        assertEquals(10, pacienteCreado.getPaciId(), "El ID debe ser asignado");
        assertTrue(pacienteCreado.getPaciEstado(), "El paciente debe estar activo");
        assertEquals("12345678", pacienteCreado.getPersona().getPersNroDoc(),
                "El DNI debe coincidir");

        verify(personasService, times(1)).existePorTipoDocYNroDoc(
                Personas.TipoDocumento.DNI, "12345678");
        verify(personasService, times(1)).guardar(any(Personas.class));
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    @DisplayName("2. Registrar Paciente - Documento Duplicado: Debe lanzar excepción")
    void registrarPaciente_DocumentoDuplicado_LanzaExcepcion() {
        // Arrange: Documento ya existe
        when(personasService.existePorTipoDocYNroDoc(
                Personas.TipoDocumento.DNI,
                "12345678"
        )).thenReturn(true);

        Personas personaExistente = new Personas();
        personaExistente.setPersId(5);
        personaExistente.setPersNombrecompleto("Otra Persona");

        when(personasService.buscarPorTipoDocYNroDoc(
                Personas.TipoDocumento.DNI,
                "12345678"
        )).thenReturn(Optional.of(personaExistente));

        // Act & Assert: Verificar que lanza excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.crearPacienteCompleto(personaTest);
        }, "Debe lanzar excepción cuando el documento ya existe");

        assertTrue(exception.getMessage().contains("Ya existe una persona con ese documento"),
                "El mensaje debe indicar documento duplicado");

        verify(personasService, never()).guardar(any(Personas.class));
        verify(pacienteRepository, never()).save(any(Paciente.class));
    }

    @Test
    @DisplayName("3. Registrar Paciente con Apoderado - Flujo Exitoso")
    void registrarPaciente_ConApoderado_CreaExitosamente() {
        // Arrange: Documento no existe, apoderado existe
        when(personasService.existePorTipoDocYNroDoc(
                Personas.TipoDocumento.DNI,
                "12345678"
        )).thenReturn(false);

        when(personasService.findById(2)).thenReturn(apoderadoTest);

        Personas personaGuardada = new Personas();
        personaGuardada.setPersId(10);
        personaGuardada.setPersNombrecompleto(personaTest.getPersNombrecompleto());

        when(personasService.guardar(any(Personas.class))).thenReturn(personaGuardada);

        Paciente pacienteEsperado = new Paciente();
        pacienteEsperado.setPaciId(10);
        pacienteEsperado.setPersona(personaGuardada);
        pacienteEsperado.setApoderado(apoderadoTest);
        pacienteEsperado.setPaciEstado(true);

        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteEsperado);

        // Act: Crear paciente con apoderado
        Paciente pacienteCreado = pacienteService.crearPacienteConApoderado(personaTest, 2);

        // Assert: Verificar creación con apoderado
        assertNotNull(pacienteCreado, "El paciente no debe ser null");
        assertNotNull(pacienteCreado.getApoderado(), "El apoderado debe estar asignado");
        assertEquals(2, pacienteCreado.getApoderado().getPersId(),
                "El ID del apoderado debe coincidir");
        assertEquals("Juan García (Padre)", pacienteCreado.getApoderado().getPersNombrecompleto(),
                "El nombre del apoderado debe coincidir");

        verify(personasService, times(1)).findById(2);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    @DisplayName("4. Registrar Paciente con Apoderado Inexistente - Debe lanzar excepción")
    void registrarPaciente_ApoderadoInexistente_LanzaExcepcion() {
        // Arrange: Documento no existe pero apoderado tampoco
        when(personasService.existePorTipoDocYNroDoc(
                Personas.TipoDocumento.DNI,
                "12345678"
        )).thenReturn(false);

        when(personasService.findById(999)).thenReturn(null); // Apoderado no existe

        // Act & Assert: Verificar que lanza excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.crearPacienteConApoderado(personaTest, 999);
        }, "Debe lanzar excepción cuando el apoderado no existe");

        assertTrue(exception.getMessage().contains("El apoderado con id 999 no existe"),
                "El mensaje debe indicar que el apoderado no existe");

        verify(personasService, never()).guardar(any(Personas.class));
        verify(pacienteRepository, never()).save(any(Paciente.class));
    }

    @Test
    @DisplayName("5. Buscar Paciente por DNI Exacto - Debe encontrar si existe")
    void buscarPaciente_PorDniExacto_EncuentraSiExiste() {
        // Arrange: Paciente existe con ese DNI
        when(pacienteRepository.findByDniExacto("12345678"))
                .thenReturn(Optional.of(pacienteTest));

        // Act: Buscar paciente
        Optional<Paciente> pacienteEncontrado = pacienteService.findByDni("12345678");

        // Assert: Verificar que se encontró
        assertTrue(pacienteEncontrado.isPresent(), "Debe encontrar el paciente");
        assertEquals("12345678", pacienteEncontrado.get().getPersona().getPersNroDoc(),
                "El DNI debe coincidir");
        assertEquals("María García López",
                pacienteEncontrado.get().getPersona().getPersNombrecompleto(),
                "El nombre debe coincidir");

        verify(pacienteRepository, times(1)).findByDniExacto("12345678");
    }

    @Test
    @DisplayName("6. Buscar Paciente por DNI - DNI vacío o null debe retornar vacío")
    void buscarPaciente_DniVacio_RetornaVacio() {
        // Act & Assert: Buscar con DNI null
        Optional<Paciente> resultadoNull = pacienteService.findByDni(null);
        assertFalse(resultadoNull.isPresent(),
                "Debe retornar vacío cuando el DNI es null");

        // Act & Assert: Buscar con DNI vacío
        Optional<Paciente> resultadoVacio = pacienteService.findByDni("");
        assertFalse(resultadoVacio.isPresent(),
                "Debe retornar vacío cuando el DNI está vacío");

        // Act & Assert: Buscar con DNI solo espacios
        Optional<Paciente> resultadoEspacios = pacienteService.findByDni("   ");
        assertFalse(resultadoEspacios.isPresent(),
                "Debe retornar vacío cuando el DNI solo tiene espacios");

        // Verificar que NO se consultó el repositorio
        verify(pacienteRepository, never()).findByDniExacto(anyString());
    }

    @Test
    @DisplayName("7. Buscar Pacientes por DNI (Autocompletado) - Debe limitar resultados")
    void buscarPacientes_Autocompletado_LimitaResultados() {
        // Arrange: Simular 15 pacientes con DNI que comienza con "123"
        List<Paciente> todosPacientes = Arrays.asList(
                crearPacienteConDni(1, "12345671"),
                crearPacienteConDni(2, "12345672"),
                crearPacienteConDni(3, "12345673"),
                crearPacienteConDni(4, "12345674"),
                crearPacienteConDni(5, "12345675"),
                crearPacienteConDni(6, "12345676"),
                crearPacienteConDni(7, "12345677"),
                crearPacienteConDni(8, "12345678"),
                crearPacienteConDni(9, "12345679"),
                crearPacienteConDni(10, "12345680"),
                crearPacienteConDni(11, "12345681"),
                crearPacienteConDni(12, "12345682"),
                crearPacienteConDni(13, "12345683"),
                crearPacienteConDni(14, "12345684"),
                crearPacienteConDni(15, "12345685")
        );

        when(pacienteRepository.findByDniStartingWith("123"))
                .thenReturn(todosPacientes);

        // Act: Buscar con límite de 10
        List<Paciente> resultados = pacienteService.buscarPorDni("123", 10);

        // Assert: Verificar que se limitó a 10 resultados
        assertNotNull(resultados, "Los resultados no deben ser null");
        assertEquals(10, resultados.size(),
                "Debe retornar máximo 10 resultados");

        verify(pacienteRepository, times(1)).findByDniStartingWith("123");
    }

    @Test
    @DisplayName("8. Contar Pacientes Activos - Debe retornar cantidad correcta")
    void contarPacientes_Activos_RetornaCantidadCorrecta() {
        // Arrange: Simular 25 pacientes activos
        when(pacienteRepository.countPacientesActivos()).thenReturn(25L);

        // Act: Contar pacientes activos
        long cantidadActivos = pacienteService.countPacientesActivos();

        // Assert: Verificar el conteo
        assertEquals(25L, cantidadActivos,
                "Debe retornar 25 pacientes activos");

        verify(pacienteRepository, times(1)).countPacientesActivos();
    }

    // Método auxiliar para crear pacientes con diferentes DNIs
    private Paciente crearPacienteConDni(int id, String dni) {
        Personas persona = new Personas();
        persona.setPersId(id);
        persona.setPersNombrecompleto("Paciente " + id);
        persona.setPersNroDoc(dni);
        persona.setPersTipoDoc(Personas.TipoDocumento.DNI);

        Paciente paciente = new Paciente();
        paciente.setPaciId(id);
        paciente.setPersona(persona);
        paciente.setPaciEstado(true);

        return paciente;
    }
}
