
package co.generation.clinica.service;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import co.generation.clinica.interfaces.Consultable;

public class ClinicaService implements Consultable {

    public List<Turno>turnos= new ArrayList<>();

    @Override
    public List <Turno> buscarPorMedico(Medico medico); {
        List<Turno> resultados = new ArrayList<>();

        if (medico==null){
            return resultados;
        }
    
    for (Turno m:this.turnos){
        if (m.getMedico()!= null && m.getMedico().equals(medico)){
            resultados.add(m);
        }
    }
    
    return resultados;
    }
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

