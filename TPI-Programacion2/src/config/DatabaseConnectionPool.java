package config;


import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DatabaseConnectionPool {

    private final static String URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/gestion_usuarios_2");

    private static final String USER = System.getProperty("db.user", "root");

    private static final String PASSWORD = System.getProperty("db.password", "Nicolasheit_123");

    
    private static final HikariConfig config = new HikariConfig();

    private static final HikariDataSource ds;
    /**
     * Bloque de inicialización estática. Se ejecuta UNA SOLA VEZ cuando la
     * clase se carga en memoria.
     *
     * Acciones: 1. Carga el driver JDBC de MySQL 2. Valida que la configuración
     * sea correcta
     *
     * Si falla, lanza ExceptionInInitializerError y detiene la aplicación. Esto
     * es intencional: sin BD correcta, la app no puede funcionar.
     */
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            validateConfiguration();
//        } catch (ClassNotFoundException e) {
//            throw new ExceptionInInitializerError("Error: No se encontró el driver JDBC de MySQL" + e.getMessage());
//        } catch (IllegalStateException e) {
//            throw new ExceptionInInitializerError("Error en la configuración de la base de datos: " + e.getMessage());
//        }
//    }
    
    // armamos el pool de conexiones, con sus config de usuario, etc
    static{
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        validateConfiguration();
        ds = new HikariDataSource(config);
    }

    /**
     * Constructor privado para prevenir instanciación. Esta es una clase
     * utilitaria con solo métodos estáticos.
     */
    private DatabaseConnectionPool() {
        throw new UnsupportedOperationException("Esta es una clase utilitaria y no debe ser instanciada");
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    /**
     * Valida que los parámetros de configuración sean válidos.
     * Llamado una sola vez desde el bloque static.
     *
     * Reglas:
     * - URL y USER no pueden ser null ni estar vacíos
     * - PASSWORD puede ser vacío (común en MySQL local root sin password)
     * - PASSWORD no puede ser null
     *
     * @throws IllegalStateException Si la configuración es inválida
     */
    private static void validateConfiguration() {
        if (URL == null || URL.trim().isEmpty()) {
            throw new IllegalStateException("La URL de la base de datos no está configurada");
        }
        if (USER == null || USER.trim().isEmpty()) {
            throw new IllegalStateException("El usuario de la base de datos no está configurado");
        }
        // PASSWORD puede ser vacío (común en MySQL local con usuario root sin contraseña)
        // Solo validamos que no sea null
        if (PASSWORD == null) {
            throw new IllegalStateException("La contraseña de la base de datos no está configurada");
        }
    }

}
