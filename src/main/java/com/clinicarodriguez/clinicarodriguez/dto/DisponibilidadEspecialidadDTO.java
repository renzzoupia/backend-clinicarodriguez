package com.clinicarodriguez.clinicarodriguez.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DisponibilidadEspecialidadDTO {
    
    private Integer especialidadId;
    private String especialidadNombre;
    private String especialidadDescripcion;
    private List<MedicoDisponibilidadDTO> medicosDisponibles;
    
    public DisponibilidadEspecialidadDTO() {
    }
    
    public DisponibilidadEspecialidadDTO(Integer especialidadId, String especialidadNombre, 
                                         String especialidadDescripcion, 
                                         List<MedicoDisponibilidadDTO> medicosDisponibles) {
        this.especialidadId = especialidadId;
        this.especialidadNombre = especialidadNombre;
        this.especialidadDescripcion = especialidadDescripcion;
        this.medicosDisponibles = medicosDisponibles;
    }

    // Getters y Setters
    public Integer getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Integer especialidadId) {
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
        private Integer medicoId;
        private String medicoNombre;
        private String medicoApellido;
        private String medicoFotoUrl;
        private String medicoEstado;
        private List<HorarioDTO> horarios;
        private List<SlotOcupadoDTO> slotsOcupados; // Slots ya reservados
        
        public MedicoDisponibilidadDTO() {
        }
        
        public MedicoDisponibilidadDTO(Integer medicoId, String medicoNombre, String medicoApellido, String medicoFotoUrl, 
                                       String medicoEstado, List<HorarioDTO> horarios) {
            this.medicoId = medicoId;
            this.medicoNombre = medicoNombre;
            this.medicoApellido = medicoApellido;
            this.medicoFotoUrl = medicoFotoUrl;
            this.medicoEstado = medicoEstado;
            this.horarios = horarios;
            this.slotsOcupados = null; // Se asignará después
        }

        public Integer getMedicoId() {
            return medicoId;
        }

        public void setMedicoId(Integer medicoId) {
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

        public List<SlotOcupadoDTO> getSlotsOcupados() {
            return slotsOcupados;
        }

        public void setSlotsOcupados(List<SlotOcupadoDTO> slotsOcupados) {
            this.slotsOcupados = slotsOcupados;
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
    
    // Clase interna para representar slot ocupado
    public static class SlotOcupadoDTO {
        private LocalDate fecha;
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private Integer citaId;
        
        public SlotOcupadoDTO() {
        }
        
        public SlotOcupadoDTO(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Integer citaId) {
            this.fecha = fecha;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
            this.citaId = citaId;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
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

        public Integer getCitaId() {
            return citaId;
        }

        public void setCitaId(Integer citaId) {
            this.citaId = citaId;
        }
    }
}
