package org.example.aplication.service;

import org.example.domain.Medico;
import org.example.interfaces.MedicoRepository;
import org.example.interfaces.MedicoService;

import java.util.List;

public class MedicoServiceImpl implements MedicoService {
    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public void agregarMedico(Medico medico) {
        medicoRepository.guardarMedico(medico);
    }

    @Override
    public List<Medico> obtenerTodosMedicos() {
        return medicoRepository.obtenerTodosMedicos();
    }

    @Override
    public Medico obtenerMedicoPorId(int id) {
        return medicoRepository.obtenerMedicoPorId(id);
    }

    @Override
    public void actualizarMedico(Medico medico) {
        medicoRepository.actualizarMedico(medico);
    }

    @Override
    public void eliminarMedico(int id) {
        medicoRepository.eliminarMedico(id);
    }
}