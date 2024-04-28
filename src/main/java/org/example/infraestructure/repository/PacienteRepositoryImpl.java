package org.example.infraestructure.repository;

import org.example.domain.Paciente;
import org.example.interfaces.PacienteRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteRepositoryImpl implements PacienteRepository {
    private static final String FILE_NAME = "pacientes.txt";
    public void guardarPaciente(Paciente paciente) {
        List<Paciente> pacientes = obtenerTodosPacientes();
        pacientes.add(paciente);
        guardarTodosPacientes(pacientes);
    }

    @Override
    public List<Paciente> obtenerTodosPacientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Paciente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Paciente obtenerPacientePorId(int id) {
        return obtenerTodosPacientes().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        List<Paciente> pacientes = obtenerTodosPacientes();
        pacientes = pacientes.stream()
                .map(p -> p.getId() == paciente.getId() ? paciente : p)
                .collect(Collectors.toList());
        guardarTodosPacientes(pacientes);
    }

    @Override
    public void eliminarPaciente(int id) {
        List<Paciente> pacientes = obtenerTodosPacientes();
        pacientes = pacientes.stream()
                .filter(p -> p.getId() != id)
                .collect(Collectors.toList());
        guardarTodosPacientes(pacientes);
    }

    private void guardarTodosPacientes(List<Paciente> pacientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(pacientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
