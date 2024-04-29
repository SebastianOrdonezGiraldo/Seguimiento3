package org.example.aplication.service;

import org.example.domain.Medico;
import org.example.interfaces.MedicoRepository;
import org.example.interfaces.MedicoService;

import java.util.List;

public class MedicoServiceImpl extends MedicoService {
    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        super(medicoRepository);
        this.medicoRepository = medicoRepository;
    }

    @Override
    public void guardarMedico(Medico medico) {
        medicoRepository.guardarMedico(medico);
    }

    @Override
    public List<Medico> obtenerTodosMedicos() {
        return medicoRepository.obtenerTodosMedicos();
    }

    @Override
    public Medico obtenerMedicoPorId(int id) {
        List<Medico> medicos = medicoRepository.obtenerTodosMedicos();
        return medicos.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}