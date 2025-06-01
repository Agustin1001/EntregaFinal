package config;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager implements AutoCloseable {
    private Connection connection;
    private boolean transactionActive = false;

    public void begin() throws SQLException {
        if (transactionActive) {
            throw new SQLException("Ya hay una transacción activa");
        }
        connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);
        transactionActive = true;
    }

    public Connection getConnection() {
        if (!transactionActive) {
            throw new IllegalStateException("No hay transacción activa");
        }
        return connection;
    }

    public void commit() throws SQLException {
        if (!transactionActive) {
            throw new SQLException("No hay transacción activa para confirmar");
        }
        try {
            connection.commit();
            System.out.println("✅ Transacción confirmada exitosamente");
        } finally {
            cleanup();
        }
    }

    public void rollback() throws SQLException {
        if (!transactionActive) {
            throw new SQLException("No hay transacción activa para revertir");
        }
        try {
            connection.rollback();
            System.out.println("⚠️ Transacción revertida");
        } finally {
            cleanup();
        }
    }

    private void cleanup() {
        transactionActive = false;
        if (connection != null) {
            DatabaseConnection.closeConnection(connection);
            connection = null;
        }
    }

    @Override
    public void close() throws SQLException {
        if (transactionActive) {
            rollback();
        }
    }
}
