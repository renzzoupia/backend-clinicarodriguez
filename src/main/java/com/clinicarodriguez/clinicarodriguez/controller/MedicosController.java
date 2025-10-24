/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/medicos")
//@Api(value = "Microservicios de gestion de pacientes", description ="Microservicio de pacientes")
public class MedicosController {
    
    @Autowired
    private MedicosRepository medicoRepository;
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Pacientes");
        result.put("data", medicoRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Medicos> findById(@PathVariable Long id) {
        Optional<Medicos> medico = medicoRepository.findById(id);

        if (medico.isPresent()) {
            return ResponseEntity.ok(medico.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();

        Optional<Medicos> data = medicoRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe médico con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            medicoRepository.deleteById(id);
            result.put("success", true);
            result.put("message", "Médico eliminado correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Medicos medico) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Carrera registrado correctamente");
        result.put("data", medicoRepository.save(medico));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Medicos medico) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Medicos> data = medicoRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe registro con Id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            Medicos existingMedico = data.get();
            existingMedico.setMediNombre(medico.getMediNombre());
            existingMedico.setMediApellido(medico.getMediApellido());
            existingMedico.setMediDni(medico.getMediDni());
            existingMedico.setMediEmail(medico.getMediEmail());
            existingMedico.setMediTelefono(medico.getMediTelefono());
            existingMedico.setMediFotoUrl(medico.getMediFotoUrl());
            existingMedico.setMediEstado(medico.getMediEstado());
            medicoRepository.save(existingMedico);

            result.put("success", true);
            result.put("message", "Datos actualizados correctamente.");
            result.put("data", existingMedico);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al actualizar: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
