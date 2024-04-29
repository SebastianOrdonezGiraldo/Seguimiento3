package org.example.interfaces;

import org.example.domain.Medico;
import org.example.interfaces.MedicoRepository;

import java.util.List;

public class MedicoService {
    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void guardarMedico(Medico medico) {
        medicoRepository.guardarMedico(medico);
    }

    public List<Medico> obtenerTodosMedicos() {
        return medicoRepository.obtenerTodosMedicos();
    }

    public Medico obtenerMedicoPorId(int id) {
        List<Medico> medicos = medicoRepository.obtenerTodosMedicos();
        return medicos.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}