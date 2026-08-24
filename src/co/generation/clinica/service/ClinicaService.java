package co.generation.clinica.service;

import co.generation.clinica.interfaces.Consultable;
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


    public void registrarPaciente(Paciente a){
        if (a == null || !a.esValido()) {
            System.out.println("Error-- El paciente contiene datos nulos o no validos");
            return;
        }

        if (pacientes.contains(a)) {
            System.out.println("Error: Ya existe un paciente registrado con la cedula " + a.getCedula());
            return;
        }

        //Asignamos el id

        int nuevoId = pacientes.stream()
                .mapToInt(Paciente::getId)
                .max()
                .orElse(0) + 1;
        a.setId(nuevoId);

        // agregamos a la lista
        pacientes.add(a);

        // mostramos en pantalla
        System.out.println("Paciente registrado: " + a.getDatosRegistro());

    }

    public Paciente buscarPorCedula(String cedula) {
        if (cedula == null) return null;

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

        // Copia de la lista original
        List<Paciente> copiaPacientes = new ArrayList<>(pacientes);

        // Ordenar por apellido y luego por nombre
        copiaPacientes.sort(Comparator.comparing(Paciente::getApellido)
                .thenComparing(Paciente::getNombre));

        for (Paciente p : copiaPacientes) {
            System.out.println(p);
        }
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
        if (fecha == null) return resultados;

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