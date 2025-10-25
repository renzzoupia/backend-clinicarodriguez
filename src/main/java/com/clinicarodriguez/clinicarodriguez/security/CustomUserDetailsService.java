package com.clinicarodriguez.clinicarodriguez.security;

import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.repository.UsuariosRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepository.findByUsuaUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        
        // Verificar que el usuario esté activo
        if (!usuario.getUsuaEsActivo()) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }
        
        // Retornar UserDetails de Spring Security
        return User.builder()
                .username(usuario.getUsuaUsername())
                .password(usuario.getUsuaClave())
                .authorities(new ArrayList<>()) // Puedes agregar roles aquí si los necesitas
                .build();
    }
}
