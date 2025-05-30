package dao;

import config.DatabaseConnection;
import model.Domicilio;
import model.Persona;
import java.sql.*;
import java.util.*;

public class PersonaDAOImpl implements GenericDAO<Persona> {

    @Override
    public void save(Persona persona) throws SQLException {
        String sql = "INSERT INTO persona (nombre, edad, id_domicilio) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, persona.getNombre());
            stmt.setInt(2, persona.getEdad());
            stmt.setInt(3,persona.getDomicilio().getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public Persona findById(int id) throws SQLException {
        String sql = "SELECT " +
                "persona.id AS persona_id, persona.nombre, persona.edad, " +
                "domicilio.id AS domicilio_id, domicilio.calle, domicilio.numero, domicilio.cp " +
                "FROM persona " +
                "JOIN domicilio ON persona.id_domicilio = domicilio.id " +
                "WHERE persona.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Domicilio domicilio = new Domicilio(
                        rs.getInt("domicilio_id"),
                        rs.getString("calle"),
                        rs.getInt("numero"),
                        rs.getInt("cp")
                );

                Persona persona = new Persona(
                        rs.getInt("persona_id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        domicilio
                );

                return persona;
            }
        }
        return null;
    }


    @Override
    public List<Persona> findAll() throws SQLException {
        List<Persona> personas = new ArrayList<>();

        String sql = "SELECT " +
                "persona.id AS persona_id, persona.nombre, persona.edad, " +
                "domicilio.id AS domicilio_id, domicilio.calle, domicilio.numero, domicilio.cp " +
                "FROM persona " +
                "JOIN domicilio ON persona.id_domicilio = domicilio.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Domicilio domicilio = new Domicilio(
                        rs.getInt("domicilio_id"),
                        rs.getString("calle"),
                        rs.getInt("numero"),
                        rs.getInt("cp")
                );

                Persona persona = new Persona(
                        rs.getInt("persona_id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        domicilio
                );

                personas.add(persona);
            }
        }

        return personas;
    }


    @Override
    public void update(Persona persona) throws SQLException {
        String sql = "UPDATE persona SET nombre=?, edad=?, id_domicilio=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, persona.getNombre());
            stmt.setInt(2, persona.getEdad());
            stmt.setInt(3, persona.getDomicilio().getId());
            stmt.setInt(4, persona.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void saveTx(Persona persona, Connection conn) throws SQLException {
        String sql = "INSERT INTO persona (nombre, edad, id_domicilio) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, persona.getNombre());
            stmt.setInt(2, persona.getEdad());
            stmt.setInt(3, persona.getDomicilio().getId());
            stmt.executeUpdate();
        }
    }
}