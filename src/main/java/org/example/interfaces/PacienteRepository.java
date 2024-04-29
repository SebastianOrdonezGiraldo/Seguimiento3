package org.example.interfaces;

import org.example.domain.Paciente;

import java.util.List;

public interface PacienteRepository {
    void guardarPaciente(Paciente paciente);
    List<Paciente> obtenerTodosPacientes();
}