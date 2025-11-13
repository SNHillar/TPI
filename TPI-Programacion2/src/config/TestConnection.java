package config;


import java.sql.Connection;
import config.DatabaseConnectionPool;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Iniciando prueba de conexión...");
        System.out.println("URL: " + System.getProperty("db.url", "jdbc:mysql://localhost:3306/gestion_usuarios_2"));
        System.out.println("User: " + System.getProperty("db.user", "root"));
        // No imprimas la contraseña por seguridad

        try {
            // 1. Intentamos llamar a tu método estático
            System.out.println("Intentando obtener conexión...");
            
            // Usamos try-with-resources para que la conexión se cierre sola
            try (Connection conn = DatabaseConnectionPool.getConnection()) {
                
                // 2. Si llega aquí, la conexión fue exitosa
                if (conn != null && conn.isValid(2)) { // conn.isValid(2) es un chequeo rápido (timeout de 2 seg)
                    System.out.println("\n-------------------------------------");
                    System.out.println("¡CONEXIÓN EXITOSA!");
                    System.out.println("Conectado a la base de datos: " + conn.getCatalog());
                    System.out.println("-------------------------------------");
                } else {
                     System.out.println("ERROR: La conexión se obtuvo pero no es válida.");
                }
            }

        } catch (SQLException e) {
            // Esto atrapa errores de MySQL (ej. contraseña incorrecta, BD no existe)
            System.out.println("\n--- ¡ERROR DE SQL! ---");
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Código de Error: " + e.getErrorCode());
            e.printStackTrace();

        } catch (ExceptionInInitializerError e) {
            // Esto atrapa los errores de tu bloque STATIC (ej. driver no encontrado)
            System.out.println("\n--- ¡ERROR DE INICIALIZACIÓN! ---");
            System.out.println("¡Falló el bloque estático de DatabaseConnection!");
            System.out.println("Causa: " + e.getCause().getMessage());
            e.printStackTrace();
        }
    }
}


    