/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import java.util.List;

/**
 *
 * @author renzo
 */
public interface MedicosService {
    public List<Medicos> findAll();

    public Medicos findById(Long id);

    public Medicos save(Medicos paciente);

    public void delete(Medicos paciente);

    public void deleteById(Long id);
}
