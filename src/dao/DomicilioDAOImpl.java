package dao;

import model.Domicilio;
import java.sql.*;
import java.util.*;

public class DomicilioDAOImpl implements GenericDAO<Domicilio> {

    @Override
    public void save(Domicilio entity) throws Exception {

    }

    @Override
    public Domicilio findById(int id) throws Exception {
        return null;
    }

    @Override
    public List<Domicilio> findAll() throws Exception {
        return List.of();
    }

    @Override
    public void update(Domicilio entity) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public void saveTx(Domicilio entity, Connection conn) throws Exception {

    }
}