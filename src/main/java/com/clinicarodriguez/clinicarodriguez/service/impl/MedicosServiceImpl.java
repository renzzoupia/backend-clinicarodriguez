/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosRepository;
import com.clinicarodriguez.clinicarodriguez.service.MedicosService;

@Service
public class MedicosServiceImpl implements MedicosService{
    @Autowired
    private MedicosRepository pacienteRepository;

    @Transactional
    @Override
    public List<Medicos> findAll() {
        return (List<Medicos>) pacienteRepository.findAll();
    }

    @Override
    public Medicos findById(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    public Medicos save(Medicos paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public void delete(Medicos paciente) {
        pacienteRepository.delete(paciente);
    }

    @Override
    public void deleteById(Long id) {
        pacienteRepository.deleteById(id);
    }
}
