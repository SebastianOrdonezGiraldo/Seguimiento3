package org.example.interfaces;

import org.example.domain.Paciente;
import org.example.interfaces.PacienteRepository;

import java.util.List;

public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public void guardarPaciente(Paciente paciente) {
        pacienteRepository.guardarPaciente(paciente);
    }

    public List<Paciente> obtenerTodosPacientes() {
        return pacienteRepository.obtenerTodosPacientes();
    }

    public Paciente obtenerPacientePorId(int id) {
        List<Paciente> pacientes = pacienteRepository.obtenerTodosPacientes();
        return pacientes.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}