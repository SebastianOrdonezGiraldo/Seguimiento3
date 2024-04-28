package org.example.domain;

import org.example.domain.Medico;
import org.example.domain.Paciente;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class HiloMedico extends Thread {
    private Medico medico;
    private BlockingQueue<Paciente> colaPacientes;
    private Paciente pacienteAtendiendo;
    private List<Paciente> pacientesAtendiendo;

    public HiloMedico(Medico medico, BlockingQueue<Paciente> colaPacientes) {
        this.medico = medico;
        this.colaPacientes = colaPacientes;

    }
    public void detener() {
        interrupt(); // Interrumpir el hilo para detenerlo
    }
    public List<Paciente> getPacientesAtendiendo() {
        return pacientesAtendiendo;
    }

    public void mostrarPacienteAtendido(Paciente paciente) {
        System.out.println(medico.getNombre() + " atendió al paciente: " + paciente.getNombre() + ", Tiempo de atención: " + paciente.getTiempoAtencion() + " segundos.");
    }
    public Medico getMedico() {
        return medico;
    }

    public Paciente getPacienteAtendido() {
        return pacienteAtendiendo;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Paciente paciente = colaPacientes.take();
                atenderPaciente(paciente);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void atenderPaciente(Paciente paciente) {
        pacienteAtendiendo = paciente;
        System.out.println(medico.getNombre() + " está atendiendo al paciente " + paciente.getNombre());

        // Simular el tiempo de atención
        try {
            Thread.sleep(paciente.getTiempoAtencion() * 1000); // Tiempo de atención en milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        medico.incrementarPacientesAtendidos();
        pacienteAtendiendo = null;
        System.out.println(medico.getNombre() + " terminó de atender al paciente " + paciente.getNombre());
    }
}