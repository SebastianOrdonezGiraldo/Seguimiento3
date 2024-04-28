package org.example.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Medico implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private TipoMedico tipo;
    private int pacientesAtendidos;
    private List<Paciente> listaPacientesAtendidos;

    public Medico(int id, String nombre, TipoMedico tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.pacientesAtendidos = 0;
        this.listaPacientesAtendidos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoMedico getTipo() {
        return tipo;
    }

    public int getPacientesAtendidos() {
        return pacientesAtendidos;
    }
    public void incrementarPacientesAtendidos() {
        pacientesAtendidos++;
    }
    public void atenderPaciente(Paciente paciente) {
        System.out.println("Médico " + nombre + " atendiendo al paciente " + paciente.getNombre());
        try {
            Thread.sleep(paciente.getTiempoAtencion() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pacientesAtendidos++;
        listaPacientesAtendidos.add(paciente); // Agregar el paciente a la lista de pacientes atendidos
        System.out.println("Médico " + nombre + " terminó de atender al paciente " + paciente.getNombre());
    }

    public List<Paciente> getListaPacientesAtendidos() {
        return listaPacientesAtendidos; // Método para obtener la lista de pacientes atendidos
    }
}
