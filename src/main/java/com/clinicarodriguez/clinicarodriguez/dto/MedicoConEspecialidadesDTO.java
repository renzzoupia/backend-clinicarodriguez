package com.clinicarodriguez.clinicarodriguez.dto;

import com.clinicarodriguez.clinicarodriguez.model.Especialidades;
import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import java.util.List;

public class MedicoConEspecialidadesDTO {
    
    private Integer mediId;
    private String mediNombre;
    private String mediFotoUrl;
    private String mediEstado;
    private Usuarios usuario;
    private List<EspecialidadSimpleDTO> especialidades;
    
    public MedicoConEspecialidadesDTO() {
    }
    
    public MedicoConEspecialidadesDTO(Integer mediId, String mediNombre, String mediFotoUrl, String mediEstado,
                                       Usuarios usuario, List<EspecialidadSimpleDTO> especialidades) {
        this.mediId = mediId;
        this.mediNombre = mediNombre;
        this.mediFotoUrl = mediFotoUrl;
        this.mediEstado = mediEstado;
        this.usuario = usuario;
        this.especialidades = especialidades;
    }

    // Getters y Setters
    public Integer getMediId() {
        return mediId;
    }

    public void setMediId(Integer mediId) {
        this.mediId = mediId;
    }

    public String getMediNombre() {
        return mediNombre;
    }

    public void setMediNombre(String mediNombre) {
        this.mediNombre = mediNombre;
    }

    public String getMediFotoUrl() {
        return mediFotoUrl;
    }

    public void setMediFotoUrl(String mediFotoUrl) {
        this.mediFotoUrl = mediFotoUrl;
    }

    public String getMediEstado() {
        return mediEstado;
    }

    public void setMediEstado(String mediEstado) {
        this.mediEstado = mediEstado;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public List<EspecialidadSimpleDTO> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<EspecialidadSimpleDTO> especialidades) {
        this.especialidades = especialidades;
    }
    
    // Clase interna para representar usuario de forma simple
    public static class UsuarioSimpleDTO {
        private Integer usuaId;
        private String usuaUsername;
        private String usuaNombrecompleto;
        private String usuaEmail;

        public UsuarioSimpleDTO() {
        }

        public UsuarioSimpleDTO(Integer usuaId, String usuaUsername, String usuaNombrecompleto, String usuaEmail) {
            this.usuaId = usuaId;
            this.usuaUsername = usuaUsername;
            this.usuaNombrecompleto = usuaNombrecompleto;
            this.usuaEmail = usuaEmail;
        }

        public Integer getUsuaId() {
            return usuaId;
        }

        public void setUsuaId(Integer usuaId) {
            this.usuaId = usuaId;
        }

        public String getUsuaUsername() {
            return usuaUsername;
        }

        public void setUsuaUsername(String usuaUsername) {
            this.usuaUsername = usuaUsername;
        }

        public String getUsuaNombrecompleto() {
            return usuaNombrecompleto;
        }

        public void setUsuaNombrecompleto(String usuaNombrecompleto) {
            this.usuaNombrecompleto = usuaNombrecompleto;
        }

        public String getUsuaEmail() {
            return usuaEmail;
        }

        public void setUsuaEmail(String usuaEmail) {
            this.usuaEmail = usuaEmail;
        }
    }
    
    // Clase interna para representar especialidades de forma simple
    public static class EspecialidadSimpleDTO {
        private Integer espeId;
        private String espeNombre;
        private String espeDescripcion;

        public EspecialidadSimpleDTO() {
        }

        public EspecialidadSimpleDTO(Integer espeId, String espeNombre, String espeDescripcion) {
            this.espeId = espeId;
            this.espeNombre = espeNombre;
            this.espeDescripcion = espeDescripcion;
        }

        public Integer getEspeId() {
            return espeId;
        }

        public void setEspeId(Integer espeId) {
            this.espeId = espeId;
        }

        public String getEspeNombre() {
            return espeNombre;
        }

        public void setEspeNombre(String espeNombre) {
            this.espeNombre = espeNombre;
        }

        public String getEspeDescripcion() {
            return espeDescripcion;
        }

        public void setEspeDescripcion(String espeDescripcion) {
            this.espeDescripcion = espeDescripcion;
        }
    }
}
