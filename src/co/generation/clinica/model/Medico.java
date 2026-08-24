package co.generation.clinica.model;

import java.util.Objects;

public class Medico {
    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad; // 1. Cambiado de String a Especialidad

    // Constructor 1: Sin ID (registros desde menú)
    public Medico(String nombre, String apellido, Especialidad especialidad) {
        setNombre(nombre);       // 3. Llama a los setters para validar y aplicar trim()
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    // Constructor 2: Con ID (reconstrucción desde CSV)
    public Medico(int id, String nombre, String apellido, Especialidad especialidad) {
        this(nombre, apellido, especialidad); // Reutiliza la lógica del primer constructor
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim(); // Se aplica trim() al guardar
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido.trim(); // Se aplica trim() al guardar
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        if (especialidad == null) {
            throw new IllegalArgumentException("La especialidad no puede ser nula.");
        }
        this.especialidad = especialidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return nombre.equalsIgnoreCase(medico.nombre) &&
                apellido.equalsIgnoreCase(medico.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase(), apellido.toLowerCase());
    }

    @Override
    public String toString() {
        return "Dr. " + nombre + " " + apellido + " - " + especialidad;
    }
}