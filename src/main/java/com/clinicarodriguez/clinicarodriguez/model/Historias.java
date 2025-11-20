package com.clinicarodriguez.clinicarodriguez.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "hist_num_historia")
    private Integer histNumHistoria;

    @Column(name = "hist_registrofecha")
    private LocalDate histRegistrofecha;

    @Column(name = "hist_estado")
    private Integer histEstado;

    @OneToMany(mappedBy = "historia")
    @JsonManagedReference
    private List<Triaje> triajes;

    @OneToMany(mappedBy = "historia")
    @JsonManagedReference
    private List<EpisodiosClinicos> episodiosClinicos;

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

    public Integer getHistNumHistoria() {
        return histNumHistoria;
    }

    public void setHistNumHistoria(Integer histNumHistoria) {
        this.histNumHistoria = histNumHistoria;
    }

    public LocalDate getHistRegistrofecha() {
        return histRegistrofecha;
    }

    public void setHistRegistrofecha(LocalDate histRegistrofecha) {
        this.histRegistrofecha = histRegistrofecha;
    }

    public Integer getHistEstado() {
        return histEstado;
    }

    public void setHistEstado(Integer histEstado) {
        this.histEstado = histEstado;
    }

    public List<Triaje> getTriajes() {
        return triajes;
    }
    public void setTriajes(List<Triaje> triajes) {
        this.triajes = triajes;
    }

    public List<EpisodiosClinicos> getEpisodiosClinicos() {
        return episodiosClinicos;
    }

    public void setEpisodiosClinicos(List<EpisodiosClinicos> episodiosClinicos) {
        this.episodiosClinicos = episodiosClinicos;
    }
}
