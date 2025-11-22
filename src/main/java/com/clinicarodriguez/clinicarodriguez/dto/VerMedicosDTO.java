package com.clinicarodriguez.clinicarodriguez.dto;

import java.util.List;

public class VerMedicosDTO {
    
    private Integer mediId;
    private String mediNombre;
    private String mediFotoUrl;
    private List<EspecialidadDTO> especialidades;
    
    public VerMedicosDTO() {
    }
    
    public VerMedicosDTO(Integer mediId, String mediNombre, String mediFotoUrl, List<EspecialidadDTO> especialidades) {
        this.mediId = mediId;
        this.mediNombre = mediNombre;
        this.mediFotoUrl = mediFotoUrl;
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

    public List<EspecialidadDTO> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<EspecialidadDTO> especialidades) {
        this.especialidades = especialidades;
    }
    
    // Clase interna para especialidades
    public static class EspecialidadDTO {
        private Integer espeId;
        private String espeNombre;
        private String espeDescripcion;

        public EspecialidadDTO() {
        }

        public EspecialidadDTO(Integer espeId, String espeNombre, String espeDescripcion) {
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
