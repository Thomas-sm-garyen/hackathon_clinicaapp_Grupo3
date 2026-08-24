package co.generation.clinica.service;

import co.generation.clinica.interfaces.Consultable;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClinicaService implements Consultable {

    public List<Turno> turnos = new ArrayList<>();
    public List<Paciente> pacientes = new ArrayList<>();
    public List<Medico> medicos = new ArrayList<>();

    public List<Turno> getTurnos() {
        return turnos;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void registrarPaciente(Paciente a) {
        if (a == null || !a.esValido()) {
            System.out.println("Error-- El paciente contiene datos nulos o no validos");
            return;
        }

        if (pacientes.contains(a)) {
            System.out.println("Error: Ya existe un paciente registrado con la cedula " + a.getCedula());
            return;
        }

        int nuevoId = pacientes.stream()
                .mapToInt(Paciente::getId)
                .max()
                .orElse(0) + 1;

        a.setId(nuevoId);

        pacientes.add(a);

        System.out.println("Paciente registrado: " + a.getDatosRegistro());
    }

    public Paciente buscarPorCedula(String cedula) {
        if (cedula == null) {
            return null;
        }

        for (Paciente paciente : pacientes) {
            if (cedula.equals(paciente.getCedula())) {
                return paciente;
            }
        }

        return null;
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados en el sistema.");
            return;
        }

        List<Paciente> copiaPacientes = new ArrayList<>(pacientes);

        copiaPacientes.sort(
                Comparator.comparing(Paciente::getApellido)
                        .thenComparing(Paciente::getNombre)
        );

        for (Paciente p : copiaPacientes) {
            System.out.println(p);
        }
    }

    public Medico buscarPorNombreApellido(String nombre, String apellido) {

        for (Medico medico : medicos) {

            if (medico.getNombre().equalsIgnoreCase(nombre)
                    && medico.getApellido().equalsIgnoreCase(apellido)) {

                return medico;
            }
        }

        return null;
    }

    public void asignarTurno(Turno t) {

        Paciente paciente = buscarPorCedula(
                t.getPaciente().getCedula()
        );

        if (paciente == null) {
            System.out.println("El paciente no está registrado.");
            return;
        }

        Medico medico = buscarPorNombreApellido(
                t.getMedico().getNombre(),
                t.getMedico().getApellido()
        );

        if (medico == null) {
            System.out.println("El médico no está registrado.");
            return;
        }

        if (turnos.contains(t)) {
            System.out.println("El médico ya tiene un turno en esa fecha y hora.");
            return;
        }

        int nuevoId = 1;

        for (Turno turno : turnos) {
            if (turno.getId() >= nuevoId) {
                nuevoId = turno.getId() + 1;
            }
        }

        t.setId(nuevoId);

        turnos.add(t);

        System.out.println("Turno asignado correctamente: " + t);
    }

    public void cancelarTurno(int idTurno) {

        Turno turnoEncontrado = null;

        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                turnoEncontrado = turno;
                break;
            }
        }

        if (turnoEncontrado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        if (turnoEncontrado.getEstado() == EstadoTurno.ATENDIDO
                || turnoEncontrado.getEstado() == EstadoTurno.CANCELADO) {

            System.out.println("El turno no se puede cancelar.");
            return;
        }

        turnoEncontrado.setEstado(EstadoTurno.CANCELADO);

        System.out.println("Turno cancelado correctamente.");
    }

    public void cambiarEstadoTurno(int idTurno, EstadoTurno nuevo) {

        Turno turnoEncontrado = null;

        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                turnoEncontrado = turno;
                break;
            }
        }

        if (turnoEncontrado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        turnoEncontrado.setEstado(nuevo);

        System.out.println("Estado actualizado a: " + nuevo);
    }

    @Override
    public List<Turno> buscarPorMedico(Medico medico) {
        List<Turno> resultados = new ArrayList<>();

        if (medico == null) {
            return resultados;
        }

        for (Turno m : this.turnos) {
            if (m.getMedico() != null && m.getMedico().equals(medico)) {
                resultados.add(m);
            }
        }

        return resultados;
    }

    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha) {
        List<Turno> resultados = new ArrayList<>();

        if (fecha == null) {
            return resultados;
        }

        for (Turno turno : turnos) {
            if (turno.getFechaHora().toLocalDate().equals(fecha)) {
                resultados.add(turno);
            }
        }

        resultados.sort(Comparator.comparing(Turno::getFechaHora));

        return resultados;
    }

    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {
        List<Turno> resultados = new ArrayList<>();

        if (paciente == null) {
            return resultados;
        }

        for (Turno t : this.turnos) {
            if (t.getPaciente() != null && t.getPaciente().equals(paciente)) {
                resultados.add(t);
            }
        }

        return resultados;
    }
}