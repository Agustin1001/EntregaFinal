package service;

import config.TransactionManager;
import dao.GenericDAO;
import model.Domicilio;
import model.Persona;
import java.util.List;

public class PersonaServiceImpl implements GenericService<Persona> {

    private final GenericDAO<Persona> personaDAO;
    private final GenericService<Domicilio> domicilioService;

    public PersonaServiceImpl(GenericDAO<Persona> personaDAO, GenericService<Domicilio> domicilioService) {
        this.personaDAO = personaDAO;
        this.domicilioService = domicilioService;
    }

    @Override
    public void save(Persona persona) throws Exception {
        validatePersona(persona);

        if (persona.getDomicilio() != null && persona.getDomicilio().getId() <= 0) {
            domicilioService.save(persona.getDomicilio());
        }

        personaDAO.save(persona);
        System.out.println("✅ Persona guardada: " + persona.getNombre());
    }


    @Override
    public Persona findById(int id) throws Exception {
        Persona persona = personaDAO.findById(id);
        if (persona == null) {
            System.out.println("⚠️ No se encontró persona con ID: " + id);
        }
        return persona;
    }

    @Override
    public List<Persona> findAll() throws Exception {
        List<Persona> personas = personaDAO.findAll();
        System.out.println("📋 Se encontraron " + personas.size() + " personas");
        return personas;
    }

    @Override
    public void update(Persona persona) throws Exception {
        validatePersona(persona);
        personaDAO.update(persona);
        System.out.println("✅ Persona actualizada: " + persona.getNombre());
    }

    @Override
    public void delete(int id) throws Exception {
        personaDAO.delete(id);
        System.out.println("🗑️ Persona eliminada con ID: " + id);
    }

    @Override
    public void saveTx(Persona persona) throws Exception {
        validatePersona(persona);

        try (TransactionManager tx = new TransactionManager()) {
            tx.begin();

            if (persona.getDomicilio() != null && persona.getDomicilio().getId() <= 0) {
                domicilioService.saveTx(persona.getDomicilio());
            }

            personaDAO.saveTx(persona, tx.getConnection());
            tx.commit();
            System.out.println("✅ Persona guardada en transacción: " + persona.getNombre());
        }
    }

    private void validatePersona(Persona persona) {

        if (persona == null) {
            throw new IllegalArgumentException("La persona no puede ser nula");
        }
        if (persona.getNombre() == null || persona.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la persona no puede estar vacío");
        }
        if (persona.getNombre().length() > 250) {
            throw new IllegalArgumentException("El nombre no puede tener más de 250 caracteres");
        }
        if (persona.getEdad() < 0 || persona.getEdad() > 150) {
            throw new IllegalArgumentException("La edad debe estar entre 0 y 150 años");
        }
        if (persona.getDomicilio() == null) {
            throw new IllegalArgumentException("La persona debe tener un domicilio asignado");
        }
    }
}