package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bdproyecto";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final int MAX_CONNECTIONS = 10;
    private static int activeConnections = 0;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Driver MySQL no encontrado");
            throw new RuntimeException("Error: No se encontró el driver JDBC.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (activeConnections >= MAX_CONNECTIONS) {
            throw new SQLException("Máximo número de conexiones alcanzado: " + MAX_CONNECTIONS);
        }

        if (URL == null || URL.isEmpty() || USER == null) {
            throw new SQLException("Configuración de la base de datos incompleta o inválida.");
        }

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            activeConnections++;
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                activeConnections--;
            } catch (SQLException e) {
                System.err.println("⚠️ Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}