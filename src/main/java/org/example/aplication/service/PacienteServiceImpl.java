package org.example.aplication.service;

import org.example.domain.Paciente;
import org.example.interfaces.PacienteRepository;
import org.example.interfaces.PacienteService;

import java.util.List;

public class PacienteServiceImpl extends PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        super(pacienteRepository);
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
