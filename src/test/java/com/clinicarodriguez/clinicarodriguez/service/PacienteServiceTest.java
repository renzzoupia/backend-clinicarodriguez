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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para PacienteService
 */
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Paciente pacienteTest;
    private Personas personaTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear persona de prueba
        personaTest = new Personas();
        personaTest.setPersId(1);
        personaTest.setPersNombrecompleto("Juan Pérez");
        personaTest.setPersNroDoc("12345678");
        personaTest.setPersTipoDoc(Personas.TipoDocumento.DNI);

        // Crear paciente de prueba
        pacienteTest = new Paciente();
        pacienteTest.setPaciId(1);
        pacienteTest.setPersona(personaTest);
        pacienteTest.setPaciEstado(true);
    }

    @Test
    @DisplayName("Debería listar todos los pacientes")
    void testFindAll() {
        // Arrange
        List<Paciente> pacientes = Arrays.asList(pacienteTest);
        when(pacienteRepository.findAll()).thenReturn(pacientes);

        // Act
        List<Paciente> resultado = pacienteService.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getPersona().getPersNombrecompleto());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería encontrar paciente por ID")
    void testFindById() {
        // Arrange
        when(pacienteRepository.findById(1)).thenReturn(Optional.of(pacienteTest));

        // Act
        Paciente resultado = pacienteService.findById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getPaciId());
        assertEquals("Juan Pérez", resultado.getPersona().getPersNombrecompleto());
        verify(pacienteRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debería retornar null cuando el paciente no existe")
    void testFindById_NotFound() {
        // Arrange
        when(pacienteRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Paciente resultado = pacienteService.findById(999);

        // Assert
        assertNull(resultado);
        verify(pacienteRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Debería guardar un paciente correctamente")
    void testSave() {
        // Arrange
        when(pacienteRepository.save(pacienteTest)).thenReturn(pacienteTest);

        // Act
        Paciente resultado = pacienteService.save(pacienteTest);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getPaciId());
        assertTrue(resultado.getPaciEstado());
        verify(pacienteRepository, times(1)).save(pacienteTest);
    }

    @Test
    @DisplayName("Debería contar pacientes activos correctamente")
    void testCountPacientesActivos() {
        // Arrange
        when(pacienteRepository.countPacientesActivos()).thenReturn(5L);

        // Act
        long count = pacienteService.countPacientesActivos();

        // Assert
        assertEquals(5L, count);
        verify(pacienteRepository, times(1)).countPacientesActivos();
    }

    @Test
    @DisplayName("Debería buscar pacientes por estado")
    void testFindByEstado() {
        // Arrange
        List<Paciente> pacientesActivos = Arrays.asList(pacienteTest);
        when(pacienteRepository.findByEstado(1)).thenReturn(pacientesActivos);

        // Act
        List<Paciente> resultado = pacienteService.findByEstado(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getPaciEstado());
        verify(pacienteRepository, times(1)).findByEstado(1);
    }

    @Test
    @DisplayName("Debería eliminar paciente por ID")
    void testDeleteById() {
        // Arrange
        doNothing().when(pacienteRepository).deleteById(1);

        // Act
        pacienteService.deleteById(1);

        // Assert
        verify(pacienteRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debería buscar paciente por DNI exacto")
    void testFindByDni() {
        // Arrange
        when(pacienteRepository.findByDniExacto("12345678")).thenReturn(Optional.of(pacienteTest));

        // Act
        Optional<Paciente> resultado = pacienteService.findByDni("12345678");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("12345678", resultado.get().getPersona().getPersNroDoc());
        verify(pacienteRepository, times(1)).findByDniExacto("12345678");
    }

    @Test
    @DisplayName("Debería retornar vacío cuando busca DNI que no existe")
    void testFindByDni_NotFound() {
        // Arrange
        when(pacienteRepository.findByDniExacto("99999999")).thenReturn(Optional.empty());

        // Act
        Optional<Paciente> resultado = pacienteService.findByDni("99999999");

        // Assert
        assertFalse(resultado.isPresent());
        verify(pacienteRepository, times(1)).findByDniExacto("99999999");
    }
}
