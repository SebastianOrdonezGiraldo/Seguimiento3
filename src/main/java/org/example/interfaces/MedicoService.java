package org.example.interfaces;

import org.example.domain.Medico;

import java.util.List;

public interface MedicoService {
    void agregarMedico(Medico medico);
    List<Medico> obtenerTodosMedicos();
    Medico obtenerMedicoPorId(int id);
    void actualizarMedico(Medico medico);
    void eliminarMedico(int id);
}