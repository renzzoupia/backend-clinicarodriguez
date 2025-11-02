package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.DiasMedico;
import com.clinicarodriguez.clinicarodriguez.repository.DiasMedicoRepository;
import com.clinicarodriguez.clinicarodriguez.service.DiasMedicoService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiasMedicoServiceImpl implements DiasMedicoService {
    
    @Autowired
    private DiasMedicoRepository diasMedicoRepository;

    @Override
    public List<DiasMedico> findAll() {
        return diasMedicoRepository.findAll();
    }

    @Override
    public DiasMedico findById(Long id) {
        return diasMedicoRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public DiasMedico save(DiasMedico diasMedico) {
        return diasMedicoRepository.save(diasMedico);
    }

    @Transactional
    @Override
    public void delete(DiasMedico diasMedico) {
        diasMedicoRepository.delete(diasMedico);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        diasMedicoRepository.deleteById(id);
    }

    @Override
    public List<DiasMedico> findByMedicoId(Long medicoId) {
        return diasMedicoRepository.findByMedicoId(medicoId);
    }

    @Override
    public List<DiasMedico> findByDiaId(Integer diaId) {
        return diasMedicoRepository.findByDiaId(diaId);
    }

    @Override
    public List<DiasMedico> findByMedicoIdAndEstadoActivo(Long medicoId) {
        return diasMedicoRepository.findByMedicoIdAndEstadoActivo(medicoId);
    }
}
