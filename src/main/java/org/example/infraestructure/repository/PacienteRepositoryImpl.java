package org.example.infraestructure.repository;

import org.example.domain.Paciente;
import org.example.interfaces.PacienteRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositoryImpl implements PacienteRepository {
    private static final String ARCHIVO_PACIENTES = "pacientes.dat";
    private List<Paciente> pacientes;

    public PacienteRepositoryImpl() {
        this.pacientes = new ArrayList<>();
    }

    @Override
    public void guardarPaciente(Paciente paciente) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_PACIENTES, true))) {
            oos.writeObject(paciente);
            pacientes.add(paciente); // Agregar el paciente a la lista en memoria
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Paciente> obtenerTodosPacientes() {
        return pacientes;
    }
}