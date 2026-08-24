package co.generation.clinica.model;

public class Paciente {
    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    public Paciente( String cedula, String nombre, String apellido, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("Este campo no puede estar vacío.");

        }
        this.cedula = cedula.trim();
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        this.nombre = nombre.trim();
    }

    public void setApellido(String apellido){
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
            if (telefono == null || !telefono.matches("^[0-9]{7,10}$")) {
                throw new IllegalArgumentException("El teléfono debe contener entre 7 y 10 dígitos numéricos.");
        }
        this.telefono = telefono;
    }


}
