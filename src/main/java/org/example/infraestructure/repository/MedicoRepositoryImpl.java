package org.example.infraestructure.repository;

import org.example.domain.Medico;
import org.example.interfaces.MedicoRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedicoRepositoryImpl implements MedicoRepository {
    private static final String FILE_NAME = "medicos.dat";

    public void guardarMedico(Medico medico) {
        List<Medico> medicos = obtenerTodosMedicos();
        medicos.add(medico);
        guardarTodosMedicos(medicos);
    }

    @Override
    public List<Medico> obtenerTodosMedicos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Medico>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Medico obtenerMedicoPorId(int id) {
        return obtenerTodosMedicos().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizarMedico(Medico medico) {
        List<Medico> medicos = obtenerTodosMedicos();
        medicos = medicos.stream()
                .map(m -> m.getId() == medico.getId() ? medico : m)
                .collect(Collectors.toList());
        guardarTodosMedicos(medicos);
    }

    @Override
    public void eliminarMedico(int id) {
        List<Medico> medicos = obtenerTodosMedicos();
        medicos = medicos.stream()
                .filter(m -> m.getId() != id)
                .collect(Collectors.toList());
        guardarTodosMedicos(medicos);
    }

    private void guardarTodosMedicos(List<Medico> medicos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(medicos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
