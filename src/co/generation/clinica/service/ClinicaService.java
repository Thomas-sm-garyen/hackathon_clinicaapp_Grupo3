package co.generation.clinica.service;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClinicaService implements Consultable {
    private List<Turno> turnos = new ArrayList<>();
    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha){
        List<Turno> resultados= new ArrayList<>();
        if (fecha == null) return resultados;
        for (Turno turno:turnos){
            if(turno.getFechaHora().toLocalDate().equals(fecha)){
                resultados.add(turno);
            };
            resultados.sort(Comparator.comparing(Turno::getFechaHora));
        };
        return resultados;
    }
}
