package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "documentos")
public class Documentos implements Serializable {

    @Id
    @Column(name = "docu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docuId;

    @ManyToOne
    @JoinColumn(name = "docu_paci_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "docu_hist_id", nullable = true)
    private Historias historia;

    @Column(name = "docu_nombre", length = 255)
    private String docuNombre;

    @Column(name = "docu_tipo", length = 100)
    private String docuTipo;

    @Column(name = "docu_url", length = 255)
    private String docuUrl;

    @Column(name = "docu_fecha_subida")
    private LocalDateTime docuFechaSubida;

    @Column(name = "docu_visible_paciente")
    private Boolean docuVisiblePaciente;

    @Column(name = "docu_confidencial")
    private Boolean docuConfidencial;

    @Column(name = "docu_estado")
    private Boolean docuEstado;

    public Long getDocuId() {
        return docuId;
    }

    public void setDocuId(Long docuId) {
        this.docuId = docuId;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Historias getHistoria() {
        return historia;
    }

    public void setHistoria(Historias historia) {
        this.historia = historia;
    }

    public String getDocuNombre() {
        return docuNombre;
    }

    public void setDocuNombre(String docuNombre) {
        this.docuNombre = docuNombre;
    }

    public String getDocuTipo() {
        return docuTipo;
    }

    public void setDocuTipo(String docuTipo) {
        this.docuTipo = docuTipo;
    }

    public String getDocuUrl() {
        return docuUrl;
    }

    public void setDocuUrl(String docuUrl) {
        this.docuUrl = docuUrl;
    }

    public LocalDateTime getDocuFechaSubida() {
        return docuFechaSubida;
    }

    public void setDocuFechaSubida(LocalDateTime docuFechaSubida) {
        this.docuFechaSubida = docuFechaSubida;
    }

    public Boolean getDocuVisiblePaciente() {
        return docuVisiblePaciente;
    }

    public void setDocuVisiblePaciente(Boolean docuVisiblePaciente) {
        this.docuVisiblePaciente = docuVisiblePaciente;
    }

    public Boolean getDocuConfidencial() {
        return docuConfidencial;
    }

    public void setDocuConfidencial(Boolean docuConfidencial) {
        this.docuConfidencial = docuConfidencial;
    }

    public Boolean getDocuEstado() {
        return docuEstado;
    }

    public void setDocuEstado(Boolean docuEstado) {
        this.docuEstado = docuEstado;
    }
}
