package co.generation.clinica.model;

import java.time.LocalDateTime;

public class Turno {
    private String id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
    }

    public Turno(String id, Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estadoTurno) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        setId(id);
        setEstadoTurno(estado);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstadoTurno() {
        return estado;
    }

    public void setEstadoTurno(EstadoTurno estadoTurno) {
        estado = estadoTurno;
    }


}

