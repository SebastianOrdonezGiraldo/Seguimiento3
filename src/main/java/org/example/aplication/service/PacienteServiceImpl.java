package org.example.aplication.service;

import org.example.domain.Paciente;
import org.example.interfaces.PacienteRepository;
import org.example.interfaces.PacienteService;

import java.util.List;

public class PacienteServiceImpl implements PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public void agregarPaciente(Paciente paciente) {
        pacienteRepository.guardarPaciente(paciente);
    }

    @Override
    public List<Paciente> obtenerTodosPacientes() {
        return pacienteRepository.obtenerTodosPacientes();
    }

    @Override
    public Paciente obtenerPacientePorId(int id) {
        return pacienteRepository.obtenerPacientePorId(id);
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        pacienteRepository.actualizarPaciente(paciente);
    }

    @Override
    public void eliminarPaciente(int id) {
        pacienteRepository.eliminarPaciente(id);
    }
}