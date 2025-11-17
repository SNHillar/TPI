package dao;

import config.DatabaseConnectionPool;
import dao.GenericDAO;
import java.util.List;
import models.Usuario;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
// etc.
// etc.

public class UsuarioDAO implements GenericDAO<Usuario> {

    private static final String INSERT_SQL = "INSERT INTO usuario (username, email) VALUES (?, ?)";

    private static final String UPDATE_SQL = "UPDATE usuario SET username = ?, email = ?, WHERE id = ?";

    private static final String DELETE_SQL = "UPDATE usuario SET eliminado = TRUE WHERE id = ?";

    private static final String SELECT_BY_ID_SQL = "SELECT u.id, u.username, u.email, u.fecha_registro "
            + "c.id AS cred_id, c.hash_password "
            + "FROM usuario u LEFT JOIN credencial_acceso c ON c.user_id = u.id "
            + "WHERE u.id = ? AND u.eliminado = FALSE";

    private static final String SELECT_BY_ALL = "SELECT u.id, u.username, u.email, u.fecha_registro "
            + "c.id AS cred_id, c.hash_password "
            + "FROM usuario u LEFT JOIN credencial_acceso c ON c.user_id = u.id "
            + "WHERE u.eliminado = FALSE";

    
    public void update(Connection conn, Usuario entidad) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
            stmt.setString(1, entidad.getUsername());
            stmt.setString(2, entidad.getEmail());
            stmt.setLong(3, entidad.getId());
            stmt.executeUpdate();
        } 
    }
    
    @Override
    public void update(Usuario entidad) throws SQLException {
        try (Connection conn = DatabaseConnectionPool.getConnection()) {
            this.update(conn, entidad);
        } catch (SQLException e) {
            System.out.println("No se pudo actualizar el usuario" + e.getMessage());
            throw e;
        }
    }

    public void delete(Connection conn, int id) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se encontro a la persona con id: " + id);
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {

        try (Connection conn = DatabaseConnectionPool.getConnection()) {
            this.delete(conn, id);
        } catch (SQLException e) {
            System.out.println("Error en la eliminacion del usuario." + e.getMessage());
            throw e;
        }
    }

    // Metodos sobrecargados, para poder manear las transacciones
    public void insert(Connection conn, Usuario user) throws SQLException {

        // ¡OJO! NO hay try-with-resources para la 'conn'
        // porque la recibimos de afuera y no queremos cerrarla.
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());

            int rowsAffected = stmt.executeUpdate();
            // si no hay, no lo insertamos
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el usuario.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int nuevoId = generatedKeys.getInt(1);
                    user.setId(nuevoId);
                } else {
                    throw new SQLException("Se inserto el usuario, pero no se pudo recuperar el ID.");
                }
            }
        }
    }

    @Override

    public void insert(Usuario user) throws SQLException {
        try (Connection conn = DatabaseConnectionPool.getConnection()) {

            this.insert(conn, user);
        } catch (SQLException e) {
            System.out.println("Error en la creacion del usuario." + e.getMessage());
            throw e;
        }
    }

    @Override
    public Usuario findById(long id) throws SQLException {
        try (Connection conn = DatabaseConnectionPool.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            } catch (SQLException e) {
                System.out.println("No se encontro por id.");
            }
        }
        return null;
    }

    @Override
    public List<Usuario> findByAll() {

        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DatabaseConnectionPool.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_BY_ALL);
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
        } catch (SQLException e) {
            System.out.println("No hay usuarios." + e.getMessage());
        }
        return usuarios;
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setUsername(rs.getString("nombre"));
        usuario.setEmail(rs.getString("email"));
        return usuario;
    }

}
