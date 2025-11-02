package com.clinicarodriguez.clinicarodriguez.dto;

import com.clinicarodriguez.clinicarodriguez.model.Especialidades;
import java.util.List;

public class MedicoConEspecialidadesDTO {
    
    private Long mediId;
    private String mediNombre;
    private String mediApellido;
    private String mediFotoUrl;
    private String mediEstado;
    private UsuarioSimpleDTO usuario;
    private List<EspecialidadSimpleDTO> especialidades;
    
    public MedicoConEspecialidadesDTO() {
    }
    
    public MedicoConEspecialidadesDTO(Long mediId, String mediNombre, String mediApellido, 
                                       String mediFotoUrl, String mediEstado,
                                       UsuarioSimpleDTO usuario, List<EspecialidadSimpleDTO> especialidades) {
        this.mediId = mediId;
        this.mediNombre = mediNombre;
        this.mediApellido = mediApellido;
        this.mediFotoUrl = mediFotoUrl;
        this.mediEstado = mediEstado;
        this.usuario = usuario;
        this.especialidades = especialidades;
    }

    // Getters y Setters
    public Long getMediId() {
        return mediId;
    }

    public void setMediId(Long mediId) {
        this.mediId = mediId;
    }

    public String getMediNombre() {
        return mediNombre;
    }

    public void setMediNombre(String mediNombre) {
        this.mediNombre = mediNombre;
    }

    public String getMediApellido() {
        return mediApellido;
    }

    public void setMediApellido(String mediApellido) {
        this.mediApellido = mediApellido;
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

    public UsuarioSimpleDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSimpleDTO usuario) {
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
        private Long usuaId;
        private String usuaUsername;
        private String usuaNombrecompleto;
        private String usuaEmail;

        public UsuarioSimpleDTO() {
        }

        public UsuarioSimpleDTO(Long usuaId, String usuaUsername, String usuaNombrecompleto, String usuaEmail) {
            this.usuaId = usuaId;
            this.usuaUsername = usuaUsername;
            this.usuaNombrecompleto = usuaNombrecompleto;
            this.usuaEmail = usuaEmail;
        }

        public Long getUsuaId() {
            return usuaId;
        }

        public void setUsuaId(Long usuaId) {
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
        private Long espeId;
        private String espeNombre;
        private String espeDescripcion;

        public EspecialidadSimpleDTO() {
        }

        public EspecialidadSimpleDTO(Long espeId, String espeNombre, String espeDescripcion) {
            this.espeId = espeId;
            this.espeNombre = espeNombre;
            this.espeDescripcion = espeDescripcion;
        }

        public Long getEspeId() {
            return espeId;
        }

        public void setEspeId(Long espeId) {
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
