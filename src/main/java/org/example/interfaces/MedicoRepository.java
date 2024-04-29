package org.example.interfaces;

import org.example.domain.Medico;

import java.util.List;

public interface MedicoRepository {
    void guardarMedico(Medico medico);
    List<Medico> obtenerTodosMedicos();
}