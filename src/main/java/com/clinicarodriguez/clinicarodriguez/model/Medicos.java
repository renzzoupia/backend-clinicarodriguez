/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "medicos")
public class Medicos implements Serializable{

    @Id
    @Column(name = "medi_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediId;

    @Column(name = "medi_nombre")
    private String mediNombre;
    
    @Column(name = "medi_apellidos")
    private String mediApellido;
    
    @Column(name = "medi_dni")
    private String mediDni;
    
    @Column(name = "medi_email")
    private String mediEmail;
    
    @Column(name = "medi_telefono")
    private String mediTelefono;
    
    @Column(name = "medi_foto_url")
    private String mediFotoUrl;
    
    @Column(name = "medi_estado")
    private String mediEstado;

    @JsonIgnore
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MedicosEspecialidades> medicosEspecialidades = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
    private List<Cita> citas = new ArrayList<>();

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

    public String getMediDni() {
        return mediDni;
    }

    public void setMediDni(String mediDni) {
        this.mediDni = mediDni;
    }

    public String getMediEmail() {
        return mediEmail;
    }

    public void setMediEmail(String mediEmail) {
        this.mediEmail = mediEmail;
    }

    public String getMediTelefono() {
        return mediTelefono;
    }

    public void setMediTelefono(String mediTelefono) {
        this.mediTelefono = mediTelefono;
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

    public Set<MedicosEspecialidades> getMedicosEspecialidades() {
        return medicosEspecialidades;
    }

    public void setMedicosEspecialidades(Set<MedicosEspecialidades> medicosEspecialidades) {
        this.medicosEspecialidades = medicosEspecialidades;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }
}
