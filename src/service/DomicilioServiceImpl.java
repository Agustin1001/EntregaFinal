package service;

import config.TransactionManager;
import dao.GenericDAO;
import model.Domicilio;
import service.GenericService;

import java.util.List;

public class DomicilioServiceImpl implements GenericService<Domicilio> {

    private final GenericDAO<Domicilio> domicilioDAO;

    public DomicilioServiceImpl(GenericDAO<Domicilio> domicilioDAO) {
        this.domicilioDAO = domicilioDAO;
    }

    @Override
    public void save(Domicilio domicilio) throws Exception {
        validateDomicilio(domicilio);
        domicilioDAO.save(domicilio);
        System.out.println("✅ Domicilio guardado con ID: " + domicilio.getId());
    }

    @Override
    public Domicilio findById(int id) throws Exception {
        Domicilio domicilio = domicilioDAO.findById(id);
        if (domicilio == null) {
            System.out.println("⚠️ No se encontró domicilio con ID: " + id);
        }
        return domicilio;
    }

    @Override
    public List<Domicilio> findAll() throws Exception {
        List<Domicilio> domicilios = domicilioDAO.findAll();
        System.out.println("📋 Se encontraron " + domicilios.size() + " domicilios");
        return domicilios;
    }

    @Override
    public void update(Domicilio domicilio) throws Exception {
        validateDomicilio(domicilio);
        domicilioDAO.update(domicilio);
        System.out.println("✅ Domicilio actualizado con ID: " + domicilio.getId());
    }

    @Override
    public void delete(int id) throws Exception {
        domicilioDAO.delete(id);
        System.out.println("🗑️ Domicilio eliminado con ID: " + id);
    }

    @Override
    public void saveTx(Domicilio domicilio) throws Exception {
        validateDomicilio(domicilio);

        try (TransactionManager tx = new TransactionManager()) {
            tx.begin();
            domicilioDAO.saveTx(domicilio, tx.getConnection());
            tx.commit();
            System.out.println("✅ Domicilio guardado en transacción con ID: " + domicilio.getId());
        }
    }

    private void validateDomicilio(Domicilio domicilio) {
        if (domicilio == null) {
            throw new IllegalArgumentException("El domicilio no puede ser nulo");
        }
        if (domicilio.getCalle() == null || domicilio.getCalle().trim().isEmpty()) {
            throw new IllegalArgumentException("La calle del domicilio no puede estar vacía");
        }
        if (domicilio.getCalle().length() > 250) {
            throw new IllegalArgumentException("La calle no puede tener más de 250 caracteres");
        }
        if (domicilio.getNumero() < 0) {
            throw new IllegalArgumentException("El número de la calle no puede ser negativo");
        }
        if (domicilio.getCp() < 1000 || domicilio.getCp() > 9999) {
            throw new IllegalArgumentException("El código postal debe tener 4 dígitos (1000-9999)");
        }
    }
}