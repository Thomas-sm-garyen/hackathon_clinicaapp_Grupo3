
package co.generation.clinica.service;

import co.generation.clinica.interfaces.Consultable;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Turno;

import java.util.List;
import java.util.ArrayList;

public class ClinicaService implements Consultable {

    public List<Turno>turnos= new ArrayList<>();

    @Override
    public List <Turno> buscarPorMedico(Medico medico); {
        List<Turno> resultados = new ArrayList<>();

        if (medico==null){
            return resultados;
        }
    }
    for (Turno m:this.turnos){
        if (m.getMedico()!= null && m.getMedico().equals(medico)){
            resultados.add(m);
        }
    }
    return resultados;
}