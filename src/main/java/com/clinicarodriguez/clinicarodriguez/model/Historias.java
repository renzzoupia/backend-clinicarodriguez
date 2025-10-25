package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "historias")
public class Historias implements Serializable {

    @Id
    @Column(name = "hist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long histId;

    @ManyToOne
    @JoinColumn(name = "hist_usua_id", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "hist_paci_id", nullable = false)
    private Paciente paciente;

    @Column(name = "hist_fecha")
    private LocalDate histFecha;

    @Column(name = "hist_edad")
    private Integer histEdad;

    @Column(name = "hist_talla", precision = 10, scale = 2)
    private BigDecimal histTalla;

    @Column(name = "hist_peso", precision = 10, scale = 3)
    private BigDecimal histPeso;

    @Column(name = "hist_pre_mmhg", length = 100)
    private String histPreMmhg;  // Presión arterial

    @Column(name = "hist_frec_res_x", length = 20)
    private String histFrecResX;  // Frecuencia respiratoria

    @Column(name = "hist_frec_cardiaca_x", precision = 10, scale = 2)
    private BigDecimal histFrecCardiacaX;

    @Column(name = "hist_imc", precision = 10, scale = 1)
    private BigDecimal histImc;  // Índice de masa corporal

    @Column(name = "hist_motivo", length = 100)
    private String histMotivo;

    @Column(name = "hist_examen_fisico", length = 255)
    private String histExamenFisico;

    @Column(name = "hist_diagnostico", columnDefinition = "TEXT")
    private String histDiagnostico;

    @Column(name = "hist_tratamiento", length = 255)
    private String histTratamiento;

    @Column(name = "hist_url_pdf", length = 255)
    private String histUrlPdf;

    @Column(name = "hist_temperatura_c", precision = 10, scale = 2)
    private BigDecimal histTemperaturaC;

    @JsonIgnore
    @OneToMany(mappedBy = "historia", cascade = CascadeType.ALL)
    private Set<Documentos> documentos = new HashSet<>();

    public Long getHistId() {
        return histId;
    }

    public void setHistId(Long histId) {
        this.histId = histId;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getHistFecha() {
        return histFecha;
    }

    public void setHistFecha(LocalDate histFecha) {
        this.histFecha = histFecha;
    }

    public Integer getHistEdad() {
        return histEdad;
    }

    public void setHistEdad(Integer histEdad) {
        this.histEdad = histEdad;
    }

    public BigDecimal getHistTalla() {
        return histTalla;
    }

    public void setHistTalla(BigDecimal histTalla) {
        this.histTalla = histTalla;
    }

    public BigDecimal getHistPeso() {
        return histPeso;
    }

    public void setHistPeso(BigDecimal histPeso) {
        this.histPeso = histPeso;
    }

    public String getHistPreMmhg() {
        return histPreMmhg;
    }

    public void setHistPreMmhg(String histPreMmhg) {
        this.histPreMmhg = histPreMmhg;
    }

    public String getHistFrecResX() {
        return histFrecResX;
    }

    public void setHistFrecResX(String histFrecResX) {
        this.histFrecResX = histFrecResX;
    }

    public BigDecimal getHistFrecCardiacaX() {
        return histFrecCardiacaX;
    }

    public void setHistFrecCardiacaX(BigDecimal histFrecCardiacaX) {
        this.histFrecCardiacaX = histFrecCardiacaX;
    }

    public BigDecimal getHistImc() {
        return histImc;
    }

    public void setHistImc(BigDecimal histImc) {
        this.histImc = histImc;
    }

    public String getHistMotivo() {
        return histMotivo;
    }

    public void setHistMotivo(String histMotivo) {
        this.histMotivo = histMotivo;
    }

    public String getHistExamenFisico() {
        return histExamenFisico;
    }

    public void setHistExamenFisico(String histExamenFisico) {
        this.histExamenFisico = histExamenFisico;
    }

    public String getHistDiagnostico() {
        return histDiagnostico;
    }

    public void setHistDiagnostico(String histDiagnostico) {
        this.histDiagnostico = histDiagnostico;
    }

    public String getHistTratamiento() {
        return histTratamiento;
    }

    public void setHistTratamiento(String histTratamiento) {
        this.histTratamiento = histTratamiento;
    }

    public String getHistUrlPdf() {
        return histUrlPdf;
    }

    public void setHistUrlPdf(String histUrlPdf) {
        this.histUrlPdf = histUrlPdf;
    }

    public BigDecimal getHistTemperaturaC() {
        return histTemperaturaC;
    }

    public void setHistTemperaturaC(BigDecimal histTemperaturaC) {
        this.histTemperaturaC = histTemperaturaC;
    }

    public Set<Documentos> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Set<Documentos> documentos) {
        this.documentos = documentos;
    }
}
