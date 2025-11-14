package config;


import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.util.Properties;

public final class DatabaseConnectionPool {

    private final static String URL;

    private static final String USER;

    private static final String PASSWORD;

    
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
    
    // armamos el pool de conexiones, con sus config de usuario, etc
    static{
        
        Properties props = new Properties();
        
        try (InputStream input = DatabaseConnectionPool.class.getClassLoader().getResourceAsStream("db.properties")) {
            
            if (input == null) {
                throw new IllegalStateException("Error: No se encontró el archivo db.properties");
            }
            
            // Cargamos las propiedades desde el archivo
            props.load(input);
        
        URL = props.getProperty("db.url");
        USER = props.getProperty("db.user");
        PASSWORD = props.getProperty("db.password");
        
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        validateConfiguration();
        ds = new HikariDataSource(config);
        
        } catch (Exception e){
            System.out.println("Error fatal al inicializar el pool de conexiones" +  e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
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
