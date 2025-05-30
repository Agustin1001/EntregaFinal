package service;
import model.Persona;
import java.util.List;

public class PersonaServiceImpl implements GenericService<Persona> {

    @Override
    public void save(Persona entity) throws Exception {

    }

    @Override
    public Persona findById(int id) throws Exception {
        return null;
    }

    @Override
    public List<Persona> findAll() throws Exception {
        return List.of();
    }

    @Override
    public void update(Persona entity) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public void saveTx(Persona entity) throws Exception {

    }
}