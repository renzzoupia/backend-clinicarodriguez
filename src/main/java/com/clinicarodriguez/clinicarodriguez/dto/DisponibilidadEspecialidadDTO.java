package com.clinicarodriguez.clinicarodriguez.dto;

import java.time.LocalTime;
import java.util.List;

public class DisponibilidadEspecialidadDTO {
    
    private Long especialidadId;
    private String especialidadNombre;
    private String especialidadDescripcion;
    private List<MedicoDisponibilidadDTO> medicosDisponibles;
    
    public DisponibilidadEspecialidadDTO() {
    }
    
    public DisponibilidadEspecialidadDTO(Long especialidadId, String especialidadNombre, 
                                         String especialidadDescripcion, 
                                         List<MedicoDisponibilidadDTO> medicosDisponibles) {
        this.especialidadId = especialidadId;
        this.especialidadNombre = especialidadNombre;
        this.especialidadDescripcion = especialidadDescripcion;
        this.medicosDisponibles = medicosDisponibles;
    }

    // Getters y Setters
    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }

    public String getEspecialidadNombre() {
        return especialidadNombre;
    }

    public void setEspecialidadNombre(String especialidadNombre) {
        this.especialidadNombre = especialidadNombre;
    }

    public String getEspecialidadDescripcion() {
        return especialidadDescripcion;
    }

    public void setEspecialidadDescripcion(String especialidadDescripcion) {
        this.especialidadDescripcion = especialidadDescripcion;
    }

    public List<MedicoDisponibilidadDTO> getMedicosDisponibles() {
        return medicosDisponibles;
    }

    public void setMedicosDisponibles(List<MedicoDisponibilidadDTO> medicosDisponibles) {
        this.medicosDisponibles = medicosDisponibles;
    }
    
    // Clase interna para representar médico con disponibilidad
    public static class MedicoDisponibilidadDTO {
        private Long medicoId;
        private String medicoNombre;
        private String medicoApellido;
        private String medicoFotoUrl;
        private String medicoEstado;
        private List<HorarioDTO> horarios;
        
        public MedicoDisponibilidadDTO() {
        }
        
        public MedicoDisponibilidadDTO(Long medicoId, String medicoNombre, String medicoApellido, String medicoFotoUrl, 
                                       String medicoEstado, List<HorarioDTO> horarios) {
            this.medicoId = medicoId;
            this.medicoNombre = medicoNombre;
            this.medicoApellido = medicoApellido;
            this.medicoFotoUrl = medicoFotoUrl;
            this.medicoEstado = medicoEstado;
            this.horarios = horarios;
        }

        public Long getMedicoId() {
            return medicoId;
        }

        public void setMedicoId(Long medicoId) {
            this.medicoId = medicoId;
        }

        public String getMedicoNombre() {
            return medicoNombre;
        }

        public void setMedicoNombre(String medicoNombre) {
            this.medicoNombre = medicoNombre;
        }

        public String getMedicoApellido() {
            return medicoApellido;
        }

        public void setMedicoApellido(String medicoApellido) {
            this.medicoApellido = medicoApellido;
        }

        public String getMedicoFotoUrl() {
            return medicoFotoUrl;
        }

        public void setMedicoFotoUrl(String medicoFotoUrl) {
            this.medicoFotoUrl = medicoFotoUrl;
        }

        public String getMedicoEstado() {
            return medicoEstado;
        }

        public void setMedicoEstado(String medicoEstado) {
            this.medicoEstado = medicoEstado;
        }

        public List<HorarioDTO> getHorarios() {
            return horarios;
        }

        public void setHorarios(List<HorarioDTO> horarios) {
            this.horarios = horarios;
        }
    }
    
    // Clase interna para representar horario
    public static class HorarioDTO {
        private Long diaId;
        private String diaNombre;
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private Integer duracion;
        private Integer estado;
        
        public HorarioDTO() {
        }
        
        public HorarioDTO(Long diaId, String diaNombre, LocalTime horaInicio, 
                         LocalTime horaFin, Integer duracion, Integer estado) {
            this.diaId = diaId;
            this.diaNombre = diaNombre;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
            this.duracion = duracion;
            this.estado = estado;
        }

        public Long getDiaId() {
            return diaId;
        }

        public void setDiaId(Long diaId) {
            this.diaId = diaId;
        }

        public String getDiaNombre() {
            return diaNombre;
        }

        public void setDiaNombre(String diaNombre) {
            this.diaNombre = diaNombre;
        }

        public LocalTime getHoraInicio() {
            return horaInicio;
        }

        public void setHoraInicio(LocalTime horaInicio) {
            this.horaInicio = horaInicio;
        }

        public LocalTime getHoraFin() {
            return horaFin;
        }

        public void setHoraFin(LocalTime horaFin) {
            this.horaFin = horaFin;
        }

        public Integer getDuracion() {
            return duracion;
        }

        public void setDuracion(Integer duracion) {
            this.duracion = duracion;
        }

        public Integer getEstado() {
            return estado;
        }

        public void setEstado(Integer estado) {
            this.estado = estado;
        }
    }
}
