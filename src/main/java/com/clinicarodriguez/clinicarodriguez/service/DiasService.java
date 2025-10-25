package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Dias;
import com.clinicarodriguez.clinicarodriguez.model.Dias.DiaSemana;
import java.util.List;
import java.util.Optional;

public interface DiasService {
    
    public List<Dias> findAll();
    
    public Dias findById(Integer id);
    
    public Dias save(Dias dia);
    
    public void delete(Dias dia);
    
    public void deleteById(Integer id);
    
    public Optional<Dias> findByDia(DiaSemana dia);
    
    public void inicializarDias();
}
