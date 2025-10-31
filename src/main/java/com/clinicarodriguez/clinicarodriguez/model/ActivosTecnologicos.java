package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "activos_tecnologicos")
public class ActivosTecnologicos implements Serializable {

    @Id
    @Column(name = "acte_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer acteId;

    @Column(name = "acte_codigo_activo", length = 50)
    private String acteCodigoActivo;

    @Column(name = "acte_nombre_equipo", length = 50)
    private String acteNombreEquipo;

    @ManyToOne
    @JoinColumn(name = "acte_cate_id", nullable = false)
    private CategoriasActivo categoria;

    @Column(name = "acte_marca", length = 50)
    private String acteMarca;

    @Column(name = "acte_modelo", length = 50)
    private String acteModelo;

    @Column(name = "acte_numero_serie", length = 50)
    private String acteNumeroSerie;

    @Column(name = "acte_fecha_compra")
    private LocalDate acteFechaCompra;

    @Column(name = "acte_estado", length = 50)
    private String acteEstado;

    @Column(name = "acte_ubicacion", length = 150)
    private String acteUbicacion;

    @ManyToOne
    @JoinColumn(name = "acte_usua_id")
    private Usuarios usuario;

    @Column(name = "acte_vida_util_anios")
    private Integer acteVidaUtilAnios;

    @Column(name = "acte_fecha_baja")
    private LocalDate acteFechaBaja;

    @Column(name = "acte_observaciones", length = 150)
    private String acteObservaciones;

    public Integer getActeId() {
        return acteId;
    }

    public void setActeId(Integer acteId) {
        this.acteId = acteId;
    }

    public String getActeCodigoActivo() {
        return acteCodigoActivo;
    }

    public void setActeCodigoActivo(String acteCodigoActivo) {
        this.acteCodigoActivo = acteCodigoActivo;
    }

    public String getActeNombreEquipo() {
        return acteNombreEquipo;
    }

    public void setActeNombreEquipo(String acteNombreEquipo) {
        this.acteNombreEquipo = acteNombreEquipo;
    }

    public CategoriasActivo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriasActivo categoria) {
        this.categoria = categoria;
    }

    public String getActeMarca() {
        return acteMarca;
    }

    public void setActeMarca(String acteMarca) {
        this.acteMarca = acteMarca;
    }

    public String getActeModelo() {
        return acteModelo;
    }

    public void setActeModelo(String acteModelo) {
        this.acteModelo = acteModelo;
    }

    public String getActeNumeroSerie() {
        return acteNumeroSerie;
    }

    public void setActeNumeroSerie(String acteNumeroSerie) {
        this.acteNumeroSerie = acteNumeroSerie;
    }

    public LocalDate getActeFechaCompra() {
        return acteFechaCompra;
    }

    public void setActeFechaCompra(LocalDate acteFechaCompra) {
        this.acteFechaCompra = acteFechaCompra;
    }

    public String getActeEstado() {
        return acteEstado;
    }

    public void setActeEstado(String acteEstado) {
        this.acteEstado = acteEstado;
    }

    public String getActeUbicacion() {
        return acteUbicacion;
    }

    public void setActeUbicacion(String acteUbicacion) {
        this.acteUbicacion = acteUbicacion;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Integer getActeVidaUtilAnios() {
        return acteVidaUtilAnios;
    }

    public void setActeVidaUtilAnios(Integer acteVidaUtilAnios) {
        this.acteVidaUtilAnios = acteVidaUtilAnios;
    }

    public LocalDate getActeFechaBaja() {
        return acteFechaBaja;
    }

    public void setActeFechaBaja(LocalDate acteFechaBaja) {
        this.acteFechaBaja = acteFechaBaja;
    }

    public String getActeObservaciones() {
        return acteObservaciones;
    }

    public void setActeObservaciones(String acteObservaciones) {
        this.acteObservaciones = acteObservaciones;
    }
}
