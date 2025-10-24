package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import java.util.List;

public interface UsuariosService {
    public List<Usuarios> findAll();

    public Usuarios findById(Long id);

    public Usuarios save(Usuarios usuario);

    public void delete(Usuarios usuario);

    public void deleteById(Long id);
}

