package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Dias;
import com.clinicarodriguez.clinicarodriguez.service.DiasService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/dias")
public class DiasController {
    
    @Autowired
    private DiasService diasService;
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Días");
        result.put("data", diasService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        Dias dia = diasService.findById(id);

        if (dia != null) {
            result.put("success", true);
            result.put("message", "Día encontrado");
            result.put("data", dia);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró el día con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Dias dia) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Día registrado correctamente");
        result.put("data", diasService.save(dia));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        Dias dia = diasService.findById(id);

        if (dia == null) {
            result.put("success", false);
            result.put("message", "No existe día con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        diasService.deleteById(id);
        result.put("success", true);
        result.put("message", "Día eliminado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping("/inicializar")
    public ResponseEntity<?> inicializar() {
        HashMap<String, Object> result = new HashMap<>();
        diasService.inicializarDias();
        result.put("success", true);
        result.put("message", "Días inicializados correctamente");
        result.put("data", diasService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
