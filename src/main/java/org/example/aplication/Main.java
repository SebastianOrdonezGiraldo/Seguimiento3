package org.example.aplication;

import org.example.aplication.service.MedicoServiceImpl;
import org.example.aplication.service.PacienteServiceImpl;
import org.example.domain.HiloMedico;
import org.example.domain.Medico;
import org.example.domain.Paciente;
import org.example.domain.TipoMedico;
import org.example.infraestructure.repository.MedicoRepositoryImpl;
import org.example.infraestructure.repository.PacienteRepositoryImpl;
import org.example.interfaces.MedicoRepository;
import org.example.interfaces.MedicoService;
import org.example.interfaces.PacienteRepository;
import org.example.interfaces.PacienteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        // Crear instancias de los repositorios
        MedicoRepository medicoRepository = new MedicoRepositoryImpl();
        PacienteRepository pacienteRepository = new PacienteRepositoryImpl();

        // Crear instancias de los servicios
        MedicoService medicoService = new MedicoServiceImpl(medicoRepository);
        PacienteService pacienteService = new PacienteServiceImpl(pacienteRepository);

        // Crear algunos médicos
        Medico medicoGeneral = new Medico(1, "Dr. García", TipoMedico.GENERAL);
        Medico medicoEspecialista = new Medico(2, "Dra. Martínez", TipoMedico.ESPECIALISTA);
        Medico medicoCirujano = new Medico(3, "Dr. Rodríguez", TipoMedico.CIRUJANO);

        medicoService.guardarMedico(medicoGeneral);
        medicoService.guardarMedico(medicoEspecialista);
        medicoService.guardarMedico(medicoCirujano);

        // Crear la cola de pacientes
        BlockingQueue<Paciente> colaPacientes = new LinkedBlockingQueue<>();

        // Crear los hilos de los médicos
        List<HiloMedico> hilosMedicos = new ArrayList<>();
        HiloMedico hiloMedicoGeneral = new HiloMedico(medicoGeneral, colaPacientes);
        HiloMedico hiloMedicoEspecialista = new HiloMedico(medicoEspecialista, colaPacientes);
        HiloMedico hiloMedicoCirujano = new HiloMedico(medicoCirujano, colaPacientes);

        hilosMedicos.add(hiloMedicoGeneral);
        hilosMedicos.add(hiloMedicoEspecialista);
        hilosMedicos.add(hiloMedicoCirujano);

        // Iniciar los hilos de los médicos
        for (HiloMedico hiloMedico : hilosMedicos) {
            hiloMedico.start();
        }

        // Agregar pacientes a la cola
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {
            int tiempoAtencion = random.nextInt(16) + 5; // Tiempo de atención aleatorio entre 5 y 20 segundos
            Paciente paciente = new Paciente(i, "Paciente " + i, tiempoAtencion);
            colaPacientes.offer(paciente);
            pacienteService.guardarPaciente(paciente);
        }

        // Esperar a que todos los pacientes sean atendidos
        while (!colaPacientes.isEmpty()) {
            try {
                Thread.sleep(1000); // Esperar 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Detener los hilos de los médicos
        for (HiloMedico hiloMedico : hilosMedicos) {
            hiloMedico.detener();
        }

        // Mostrar la cantidad de pacientes atendidos por cada médico
        System.out.println("Cantidad de pacientes atendidos por cada médico:");
        List<Medico> medicos = medicoService.obtenerTodosMedicos();
        for (Medico medico : medicos) {
            System.out.println(medico.getNombre() + ": " + medico.getPacientesAtendidos());
        }
    }
}