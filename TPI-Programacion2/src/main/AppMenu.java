package main;

import dao.CredencialAccesoDAO;
import dao.CredencialAccesoDAOImpl;
import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import dao.UsuarioDAOImpl;
import models.Usuario;
import service.UsuarioServiceImpl;
import java.util.Scanner;
import service.CredencialAccesoService;
import service.CredencialAccesoServiceImpl;
import service.UsuarioService;


public class AppMenu {
    
    // Creamos nuestras implementaciones de DAO
    UsuarioDAO usuarioDao = new UsuarioDAOImpl();
    CredencialAccesoDAO credencialDao = new CredencialAccesoDAOImpl();
    
    private final Scanner sc = new Scanner(System.in);
    
    private final UsuarioService usuarioService = new UsuarioServiceImpl(usuarioDao, credencialDao);
    private final CredencialAccesoService credencialService = new CredencialAccesoServiceImpl(credencialDao, usuarioDao);

    public void iniciar() {
        String opcion;

        do {
            mostrarMenu();
            opcion = sc.nextLine().trim().toUpperCase(); // MAYÚSCULAS

            switch (opcion) {
                case "1" -> crearUsuario();
                case "2" -> iniciarSesion();
                case "3" -> buscarPorId();
                case "4" -> listarUsuarios();
                case "5" -> actualizarUsuario();
                case "6" -> eliminarUsuario();
                case "7" -> findByEmail();
                case "8" -> restoreUser();
                case "0" -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion inválida.");
            }

        } while (!opcion.equals("0"));
    }

    private void mostrarMenu() {
        System.out.println("\n======== GESTION DE USUARIOS ========");
        System.out.println("1) Crear usuario (A + credencial B)");
        System.out.println("2) Iniciar Sesion");
        System.out.println("3) Buscar usuario por ID");
        System.out.println("4) Listar usuarios");
        System.out.println("5) Actualizar usuario");
        System.out.println("6) Eliminar usuario");
        System.out.println("7) Buscar por EMAIL (campo relevante)");
        System.out.println("8) Restaurar usuario");
        System.out.println("0) Salir");
        System.out.print("Seleccione una opción: ");
    }

    // CREAR
    private void crearUsuario() {
        try {
            System.out.println("Username:");
            String username = sc.nextLine();

            System.out.println("Email:");
            String email = sc.nextLine();

            System.out.println("Contraseña:");
            String passwordPlano = sc.nextLine();
            
            // Crear usuario (A)
            Usuario u = new Usuario();
            u.setUsername(username);
            u.setEmail(email);

            usuarioService.registrarUsuario(u, passwordPlano);

            System.out.println("Usuario creado con éxito.");

        } catch (Exception e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
        }
    }
    
    private void iniciarSesion() {
        try {
            System.out.println("Ingrese su usuario: ");
            String username = sc.nextLine();
            
            System.out.println("Ingrese su contrasenia: ");
            String password = sc.nextLine();
            
            Usuario usuarioLogueado = credencialService.login(username, password);
            
            System.out.println("LOGIN EXITOSO! Bienvenido! " + usuarioLogueado.getUsername());
        } catch (Exception e) {
            System.out.println("Error al loguearse" + e.getMessage());
        }
    }

    // BUSCAR POR ID
    private void buscarPorId() {
        try {
            System.out.print("Ingrese ID: ");
            long id = Long.parseLong(sc.nextLine());

            Usuario u = usuarioService.findById(id);
            if (u == null) {
                System.out.println("No existe usuario con ese ID.");
            } else {
                System.out.println("Usuario encontrado:");
                System.out.println(u);
                System.out.println("Credencial: " + u.getCredencial());
            }

        } catch (NumberFormatException e) {
            System.out.println("ID invalido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // LISTAR
    private void listarUsuarios() {
        try {
            var lista = usuarioService.findByAll();
            if (lista.isEmpty()) {
                System.out.println("No hay usuarios disponibles.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
    }

    // ACTUALIZAR
    private void actualizarUsuario() {
        try {
            System.out.print("ID a actualizar: ");
            long id = Long.parseLong(sc.nextLine());

            Usuario u = usuarioService.findById(id);
            if (u == null) {
                System.out.println("No existe ese usuario.");
                return;
            }

            System.out.println("Nuevo email:");
            String email = sc.nextLine();

            System.out.println("¿Activo? (S/N):");
            boolean activo = sc.nextLine().trim().equalsIgnoreCase("S");

            u.setEmail(email);
            u.setActivo(activo);

            usuarioService.update(u);

            System.out.println("Usuario actualizado.");

        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    // ✔ ELIMINAR (baja lógica)
    private void eliminarUsuario() {
        try {
            System.out.print("ID a eliminar: ");
            long id = Long.parseLong(sc.nextLine());

            usuarioService.delete(id);

            System.out.println("Usuario eliminado logicamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
    // TO DO:
    
    
    // RESTAURAR
    private void restoreUser() {
        try {
            System.out.print("ID a restaurar: ");
            long id = Long.parseLong(sc.nextLine());

            usuarioService.restore(id);

            System.out.println("Usuario restaurado.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // BÚSQUEDA POR CAMPO RELEVANTE (EMAIL)
    private void findByEmail() {
        try {
            System.out.print("Ingrese email a buscar: ");
            String email = sc.nextLine();

            var resultado = usuarioService.findByEmail(email);

            if (resultado == null) {
                System.out.println("No se encontró usuario con ese email.");
            } else {
                System.out.println("Usuario encontrado:");
                System.out.println(resultado);
            }

        } catch (Exception e) {
            System.out.println("Error en búsqueda: " + e.getMessage());
        }
    }  
//    
}
