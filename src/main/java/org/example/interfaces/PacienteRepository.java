package org.example.interfaces;

import org.example.domain.Paciente;

import java.util.List;

public interface PacienteRepository {
    void guardarPaciente(Paciente paciente);
    List<Paciente> obtenerTodosPacientes();
    Paciente obtenerPacientePorId(int id);
    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(int id);
}
