package com.clinicarodriguez.clinicarodriguez.repository;

import com.clinicarodriguez.clinicarodriguez.model.Dias;
import com.clinicarodriguez.clinicarodriguez.model.Dias.DiaSemana;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiasRepository extends JpaRepository<Dias, Integer> {
    
    Optional<Dias> findByDia(DiaSemana dia);
    
    boolean existsByDia(DiaSemana dia);
}
