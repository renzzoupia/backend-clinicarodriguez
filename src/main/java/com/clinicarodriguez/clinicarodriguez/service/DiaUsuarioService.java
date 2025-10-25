package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.DiaUsuario;
import java.util.List;

public interface DiaUsuarioService {
    
    public List<DiaUsuario> findAll();
    
    public DiaUsuario findById(Long id);
    
    public DiaUsuario save(DiaUsuario diaUsuario);
    
    public void delete(DiaUsuario diaUsuario);
    
    public void deleteById(Long id);
    
    public List<DiaUsuario> findByUsuarioId(Long usuarioId);
    
    public List<DiaUsuario> findByDiaId(Integer diaId);
    
    public List<DiaUsuario> findByUsuarioIdAndEstadoActivo(Long usuarioId);
}
