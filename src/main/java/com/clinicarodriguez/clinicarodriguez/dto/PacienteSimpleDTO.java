package com.clinicarodriguez.clinicarodriguez.dto;

import com.clinicarodriguez.clinicarodriguez.model.Personas.TipoDocumento;

/**
 * DTO simplificado para búsquedas rápidas de pacientes (autocompletado)
 */
public class PacienteSimpleDTO {
    
    private Integer paciId;
    private String nombrecompleto;
    private TipoDocumento tipoDoc;
    private String nroDoc;
    
    // Constructor vacío
    public PacienteSimpleDTO() {
    }
    
    // Constructor completo
    public PacienteSimpleDTO(Integer paciId, String nombrecompleto, TipoDocumento tipoDoc, String nroDoc) {
        this.paciId = paciId;
        this.nombrecompleto = nombrecompleto;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
    }

    // Getters y Setters
    public Integer getPaciId() {
        return paciId;
    }

    public void setPaciId(Integer paciId) {
        this.paciId = paciId;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public TipoDocumento getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDocumento tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }
}
