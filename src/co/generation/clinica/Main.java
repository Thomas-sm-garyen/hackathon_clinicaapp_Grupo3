package co.generation.clinica;

import co.generation.clinica.model.Especialidad;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;
import co.generation.clinica.service.ClinicaService;
//import co.generation.clinica.datos.DatosCSV;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Secuencia en main():
        ClinicaService servicio = new ClinicaService();
        //DatosCSV.cargar(servicio);
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        // bucle while con Scanner para el menú...

        while (!salir) {
            mostrarMenu();
            System.out.println("Seleccione una opción");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "0":
                    //DatosCSV.guardar(servicio);
                    System.out.println("Hasta pronto. Datos guardados.");
                    salir = true;
                    break;
                case"1":
                    servicio.registrarPaciente();
                case "3":
                    asignarTurno(servicio,scanner);
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
        System.out.println("-----------------------------------------");
    }

    //OPCION 3

    private static void asignarTurno(ClinicaService servicio, Scanner scan){
        System.out.println("\n--- ASIGNAR TURNO ---");
        System.out.println("Ingresa la cédula del paciente");
        String cedula = scan.nextLine();
        Paciente paciente= servicio.buscarPorCedula(cedula);
        if (paciente == null) {
            System.out.println("Error: No se encontró ningún paciente con esa cédula.");
            return;
        }
        System.out.print("Ingrese el nombre del médico: ");
        String nombreMedico = scan.nextLine();
        System.out.print("Ingrese el apellido del médico: ");
        String apellidoMedico = scan.nextLine();
        Medico medico = servicio.buscarPorNombreApellido(nombreMedico, apellidoMedico);

        if (medico == null) {
            System.out.println("Error: No se encontró ningún médico con ese nombre y apellido.");
            return;
        }
        try {
            System.out.print("Ingrese el año (ej. 2026): ");
            int anio = Integer.parseInt(scan.nextLine());

            System.out.print("Ingrese el mes (1-12): ");
            int mes = Integer.parseInt(scan.nextLine());

            System.out.print("Ingrese el día (1-31): ");
            int dia = Integer.parseInt(scan.nextLine());

            System.out.print("Ingrese la hora (0-23): ");
            int hora = Integer.parseInt(scan.nextLine());

            System.out.print("Ingrese los minutos (0-59): ");
            int minuto = Integer.parseInt(scan.nextLine());
            LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minuto);
            Turno nuevoTurno = new Turno(paciente, medico, fechaHora);
            servicio.asignarTurno(nuevoTurno);

            System.out.println("¡Turno asignado con éxito!");
        } catch (Exception e) {
            System.out.println("Error al ingresar los datos de fecha/hora. Verifique que los números sean válidos.");
        }
    }
}
