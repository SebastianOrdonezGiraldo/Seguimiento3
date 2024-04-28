package org.example.aplication;

import org.example.domain.HiloMedico;
import org.example.domain.Medico;
import org.example.domain.Paciente;
import org.example.domain.TipoMedico;
import org.example.infraestructure.repository.MedicoRepositoryImpl;
import org.example.interfaces.MedicoRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Medico> medicos;
    private static BlockingQueue<Paciente> colaPacientes;
    private static List<HiloMedico> hilosMedicos;

    public static void main(String[] args) {
        medicos = new ArrayList<>();
        colaPacientes = new LinkedBlockingQueue<>();
        hilosMedicos = new ArrayList<>();

        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:

                    iniciarSimulacion();
                    break;
                case 2:
                    mostrarMedicosYPacientes();
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 3);

        // Cerrar el scanner al final
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== HOSPITAL DEL SUR =====");
        System.out.println("1. Iniciar simulación");
        System.out.println("2. Mostrar médicos y pacientes");
        System.out.println("3. Salir");
        System.out.print("Ingrese una opción: ");
    }
    private static void ingresarDatosPaciente() {
        System.out.print("Ingrese el ID del paciente: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        System.out.print("Ingrese el nombre del paciente: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el tiempo de atención del paciente (en segundos): ");
        int tiempoAtencion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Paciente paciente = new Paciente(id, nombre, tiempoAtencion);
        guardarPacienteEnArchivo(paciente);
    }
    private static void guardarPacienteEnArchivo(Paciente paciente) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pacientes.dat", true))) {
            oos.writeObject(paciente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void iniciarSimulacion() {
        // Crear algunos médicos
        Medico medicoGeneral = new Medico(1, "Dr. García", TipoMedico.GENERAL);
        Medico medicoEspecialista = new Medico(2, "Dra. Martínez", TipoMedico.ESPECIALISTA);
        Medico medicoCirujano = new Medico(3, "Dr. Rodríguez", TipoMedico.CIRUJANO);
        long tiempoInicio = System.currentTimeMillis();
        long duracionSimulacionSegundos = 30; // Duración deseada en segundos
        boolean simulacionCompletada = false;
        medicos.add(medicoGeneral);
        medicos.add(medicoEspecialista);
        medicos.add(medicoCirujano);

        // Crear los hilos de los médicos
        HiloMedico hiloMedicoGeneral = new HiloMedico(medicoGeneral, colaPacientes);
        HiloMedico hiloMedicoEspecialista = new HiloMedico(medicoEspecialista, colaPacientes);
        HiloMedico hiloMedicoCirujano = new HiloMedico(medicoCirujano, colaPacientes);

        hilosMedicos.add(hiloMedicoGeneral);
        hilosMedicos.add(hiloMedicoEspecialista);
        hilosMedicos.add(hiloMedicoCirujano);

        // Iniciar los hilos de los médicos
        hiloMedicoGeneral.start();
        hiloMedicoEspecialista.start();
        hiloMedicoCirujano.start();

        // Agregar pacientes a la cola
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {
            int tiempoAtencion = random.nextInt(16) + 5; // Tiempo de atención aleatorio entre 5 y 20 segundos
            Paciente paciente = new Paciente(i, "Paciente " + i, tiempoAtencion);
            colaPacientes.offer(paciente);
        }

        // Simular la atención de pacientes hasta que la cola esté vacía
        try {
            // Simular la atención de pacientes hasta que la cola esté vacía o haya pasado el tiempo límite
            while (!colaPacientes.isEmpty() && (System.currentTimeMillis() - tiempoInicio) / 1000 < duracionSimulacionSegundos) {
                try {
                    Thread.sleep(1000); // Esperar 1 segundo para simular la atención
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            simulacionCompletada = true; // Indicar que la simulación se completó correctamente
        } catch (Exception e) {
            System.out.println("Se produjo un error durante la simulación: " + e.getMessage());
        }

        // Detener los hilos de los médicos
        for (HiloMedico hiloMedico : hilosMedicos) {
            hiloMedico.detener(); // Agrega un método detener() en tu clase HiloMedico para finalizar el hilo adecuadamente
        }

        // Mostrar la cantidad de pacientes atendidos por cada médico
        System.out.println("Cantidad de pacientes atendidos por cada médico:");
        for (Medico medico : medicos) {
            System.out.println(medico.getNombre() + ": " + medico.getPacientesAtendidos());
        }

        // Guardar los médicos y sus pacientes atendidos al final
        MedicoRepository medicoRepository = new MedicoRepositoryImpl();
        for (Medico medico : medicos) {
            medicoRepository.guardarMedico(medico);
        }

        if (simulacionCompletada) {
            System.out.println("\nSimulación completada con éxito.");
        } else {
            System.out.println("\nLa simulación no se completó debido a un error.");
        }
    }

    private static void mostrarMedicosYPacientes() {
        System.out.println("\nMédicos:");
        for (Medico medico : medicos) {
            System.out.println("- " + medico.getNombre() + " (" + medico.getTipo() + ")");
        }

        System.out.println("\nPacientes en la cola:");
        for (Paciente paciente : colaPacientes) {
            System.out.println("- " + paciente.getNombre() + " (Tiempo de atención: " + paciente.getTiempoAtencion() + " segundos)");
        }

        System.out.println("\nMédicos y pacientes que están atendiendo:");
        for (HiloMedico hiloMedico : hilosMedicos) {
            Medico medico = hiloMedico.getMedico();
            System.out.println("- " + medico.getNombre() + " (" + medico.getTipo() + ")");
            for (Paciente paciente : hiloMedico.getPacientesAtendiendo()) {
                System.out.println("  - " + paciente.getNombre());
            }
        }
    }
}