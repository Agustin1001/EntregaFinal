package dao;

import config.DatabaseConnection;
import model.Domicilio;
import java.sql.*;
import java.util.*;

public class DomicilioDAOImpl implements GenericDAO<Domicilio> {

    @Override
    public void save(Domicilio domicilio) throws SQLException {
        String sql = "INSERT INTO domicilio (calle, numero, cp) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, domicilio.getCalle());
            stmt.setInt(2, domicilio.getNumero());
            stmt.setInt(3, domicilio.getCp());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al insertar domicilio, no se afectaron filas");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    domicilio.setId(generatedKeys.getInt(1));
                }
            }
        }
    }
    public int saveAndGetId(Domicilio domicilio) throws SQLException {
        save(domicilio);
        return domicilio.getId();
    }

    @Override
    public Domicilio findById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID debe ser mayor a 0");
        }

        String sql = "SELECT * FROM domicilio WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Domicilio(
                            rs.getInt("id"),
                            rs.getString("calle"),
                            rs.getInt("numero"),
                            rs.getInt("cp")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Domicilio> findAll() throws SQLException {
        String sql = "SELECT * FROM domicilio ORDER BY id";
        List<Domicilio> domicilios = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                domicilios.add(new Domicilio(
                        rs.getInt("id"),
                        rs.getString("calle"),
                        rs.getInt("numero"),
                        rs.getInt("cp")
                ));
            }
        }
        return domicilios;
    }

    @Override
    public void update(Domicilio domicilio) throws SQLException {
        if (domicilio.getId() <= 0) {
            throw new IllegalArgumentException("ID del domicilio debe ser mayor a 0");
        }

        String sql = "UPDATE domicilio SET calle=?, numero=?, cp=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, domicilio.getCalle());
            stmt.setInt(2, domicilio.getNumero());
            stmt.setInt(3, domicilio.getCp());
            stmt.setInt(4, domicilio.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se encontró domicilio con ID: " + domicilio.getId());
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID debe ser mayor a 0");
        }

        String sql = "DELETE FROM domicilio WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se encontró domicilio con ID: " + id);
            }
        }
    }

    @Override
    public void saveTx(Domicilio domicilio, Connection conn) throws SQLException {
        String sql = "INSERT INTO domicilio (calle, numero, cp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, domicilio.getCalle());
            stmt.setInt(2, domicilio.getNumero());
            stmt.setInt(3, domicilio.getCp());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al insertar domicilio en transacción");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    domicilio.setId(generatedKeys.getInt(1));
                }
            }
        }
    }
}