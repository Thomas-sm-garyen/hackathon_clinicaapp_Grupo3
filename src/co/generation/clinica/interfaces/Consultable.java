package co.generation.clinica.interfaces;

import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;

import java.util.List;
import java.time.LocalDate;


public interface Consultable {
    List <Turno>listarTurnosDelDia(LocalDate fecha);
    List <Turno> buscarPorMedico(Medico medico);
    List <Turno> buscarPorPaciente(Paciente paciente);
}
