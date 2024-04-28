package org.example.domain;

import org.example.domain.Medico;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;
    private Paciente pacienteAtendiendo;
    private int id;
    private String nombre;
    private int tiempoAtencion;

    public Paciente(int id, String nombre, int tiempoAtencion) {
        this.id = id;
        this.nombre = nombre;
        this.tiempoAtencion = tiempoAtencion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempoAtencion() {
        return tiempoAtencion;
    }

    public Paciente getPacienteAtendido() {
        return pacienteAtendiendo;
    }
}

