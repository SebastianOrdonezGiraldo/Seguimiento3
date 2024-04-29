package org.example.infraestructure.repository;

import org.example.domain.Medico;
import org.example.interfaces.MedicoRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepositoryImpl implements MedicoRepository {
    private static final String ARCHIVO_MEDICOS = "medicos.dat";
    private List<Medico> medicos;

    public MedicoRepositoryImpl() {
        this.medicos = new ArrayList<>();
    }

    @Override
    public void guardarMedico(Medico medico) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_MEDICOS, true))) {
            oos.writeObject(medico);
            medicos.add(medico); // Agregar el médico a la lista en memoria
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Medico> obtenerTodosMedicos() {
        return medicos;
    }
}