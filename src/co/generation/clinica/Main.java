package co.generation.clinica;

// 1. Importación actualizada para la clase DatosCSV
import co.generation.clinica.datos.DatosCSV;

import co.generation.clinica.model.Especialidad;
import co.generation.clinica.model.EstadoTurno;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;
import co.generation.clinica.service.ClinicaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ClinicaService servicio = new ClinicaService();

        // Carga los archivos CSV de la carpeta datos/
        DatosCSV.cargar(servicio);

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    opcionRegistrarPaciente(servicio, scanner);
                    break;
                case "2":
                    opcionRegistrarMedico(servicio, scanner);
                    break;
                case "3":
                    opcionAsignarTurno(servicio, scanner);
                    break;
                case "4":
                    opcionListarTurnosDelDia(servicio, scanner);
                    break;
                case "5":
                    opcionCancelarTurno(servicio, scanner);
                    break;
                case "6":
                    opcionVerTurnosPorMedico(servicio, scanner);
                    break;
                case "7":
                    opcionVerTurnosPorPaciente(servicio, scanner);
                    break;
                case "8":
                    opcionCambiarEstadoTurno(servicio, scanner);
                    break;
                case "9":
                    servicio.listarPacientes();
                    break;
                case "10":
                    servicio.listarMedicos();
                    break;
                case "0":
                    // Guarda todos los cambios en los archivos CSV al salir
                    DatosCSV.guardar(servicio);
                    System.out.println("Hasta pronto. Datos guardados.");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("|          CLINICAAPP – MENÚ            |");
        System.out.println("|---------------------------------------|");
        System.out.println("|  1. Registrar paciente                |");
        System.out.println("|  2. Registrar médico                  |");
        System.out.println("|  3. Asignar turno                     |");
        System.out.println("|  4. Listar turnos del día             |");
        System.out.println("|  5. Cancelar turno                    |");
        System.out.println("|  6. Ver turnos por médico             |");
        System.out.println("|  7. Ver turnos por paciente           |");
        System.out.println("|  8. Cambiar estado de turno           |");
        System.out.println("|  9. Listar pacientes                  |");
        System.out.println("| 10. Listar médicos                    |");
        System.out.println("|  0. Salir                             |");
        System.out.println("-----------------------------------------");}

    private static void opcionRegistrarPaciente(ClinicaService servicio, Scanner scanner) {
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        Paciente paciente = new Paciente(cedula, nombre, apellido, telefono);
        servicio.registrarPaciente(paciente);
    }

    private static void opcionRegistrarMedico(ClinicaService servicio, Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Especialidad (CARDIOLOGIA, URGENCIAS, etc.): ");
        String espStr = scanner.nextLine();

        try {
            Especialidad especialidad = Especialidad.valueOf(espStr.toUpperCase());
            Medico medico = new Medico(nombre, apellido, especialidad);
            servicio.registrarMedico(medico);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Especialidad no válida.");
        }
    }

    private static void opcionAsignarTurno(ClinicaService servicio, Scanner scanner) {
        System.out.print("Cédula del paciente: ");
        String cedula = scanner.nextLine();
        Paciente paciente = servicio.buscarPorCedula(cedula);

        if (paciente == null) {
            System.out.println("Error: El paciente no está registrado.");
            return;
        }

        System.out.print("Nombre del médico: ");
        String nombreM = scanner.nextLine();
        System.out.print("Apellido del médico: ");
        String apellidoM = scanner.nextLine();
        Medico medico = servicio.buscarPorNombreApellido(nombreM, apellidoM);

        if (medico == null) {
            System.out.println("Error: El médico no está registrado.");
            return;
        }

        try {
            System.out.print("Año (ej. 2026): ");
            int anio = Integer.parseInt(scanner.nextLine());
            System.out.print("Mes (1-12): ");
            int mes = Integer.parseInt(scanner.nextLine());
            System.out.print("Día (1-31): ");
            int dia = Integer.parseInt(scanner.nextLine());
            System.out.print("Hora (0-23): ");
            int hora = Integer.parseInt(scanner.nextLine());
            System.out.print("Minuto (0-59): ");
            int minuto = Integer.parseInt(scanner.nextLine());

            LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minuto);
            Turno nuevoTurno = new Turno(paciente, medico, fechaHora);
            servicio.asignarTurno(nuevoTurno);
        } catch (Exception e) {
            System.out.println("Error: Fecha u hora inválida.");
        }
    }

    private static void opcionListarTurnosDelDia(ClinicaService servicio, Scanner scanner) {
        System.out.print("Ingrese fecha (AAAA-MM-DD): ");
        try {
            LocalDate fecha = LocalDate.parse(scanner.nextLine());
            List<Turno> turnos = servicio.listarTurnosDelDia(fecha);
            imprimirTurnos(turnos);
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha inválido.");
        }
    }

    private static void opcionCancelarTurno(ClinicaService servicio, Scanner scanner) {
        try {
            System.out.print("ID del turno a cancelar: ");
            int id = Integer.parseInt(scanner.nextLine());
            servicio.cancelarTurno(id);
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número de ID válido.");
        }
    }

    private static void opcionVerTurnosPorMedico(ClinicaService servicio, Scanner scanner) {
        System.out.print("Nombre del médico: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido del médico: ");
        String apellido = scanner.nextLine();

        Medico medico = servicio.buscarPorNombreApellido(nombre, apellido);
        if (medico == null) {
            System.out.println("No existe un médico registrado con ese nombre y apellido.");
            return;
        }

        List<Turno> turnos = servicio.buscarPorMedico(medico);
        imprimirTurnos(turnos);
    }

    private static void opcionVerTurnosPorPaciente(ClinicaService servicio, Scanner scanner) {
        System.out.print("Cédula del paciente: ");
        String cedula = scanner.nextLine();

        Paciente paciente = servicio.buscarPorCedula(cedula);
        if (paciente == null) {
            System.out.println("No existe un paciente registrado con esa cédula.");
            return;
        }

        List<Turno> turnos = servicio.buscarPorPaciente(paciente);
        imprimirTurnos(turnos);
    }

    private static void opcionCambiarEstadoTurno(ClinicaService servicio, Scanner scanner) {
        try {
            System.out.print("ID del turno: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Nuevo estado (PENDIENTE, ATENDIDO, CANCELADO): ");
            String estadoStr = scanner.nextLine();
            EstadoTurno nuevoEstado = EstadoTurno.valueOf(estadoStr.toUpperCase());

            servicio.cambiarEstadoTurno(id, nuevoEstado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: ID numérico o Estado no válido.");
        }
    }

    private static void imprimirTurnos(List<Turno> turnos) {
        if (turnos.isEmpty()) {
            System.out.println("No se encontraron turnos registrados.");
        } else {
            for (Turno t : turnos) {
                System.out.println(t);
            }
        }
    }
}