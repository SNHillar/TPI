package main;

import java.time.LocalDate;
import models.Usuario;
import models.CredencialAcceso;
import service.UsuarioServiceImpl;
import java.time.LocalDateTime;
import java.util.Scanner;


public class AppMenu {
    
    private final Scanner sc = new Scanner(System.in);
    private final UsuarioServiceImpl usuarioService = new UsuarioServiceImpl();

    public void iniciar() {
        String opcion;

        do {
            mostrarMenu();
            opcion = sc.nextLine().trim().toUpperCase(); // MAYÚSCULAS

            switch (opcion) {
                case "1" -> crearUsuario();
                case "2" -> buscarPorId();
                case "3" -> listarUsuarios();
                case "4" -> actualizarUsuario();
                case "5" -> eliminarUsuario();
                case "6" -> restaurarUsuario();
                case "7" -> buscarPorEmail(); // búsqueda adicional
                case "0" -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("⚠ Opción inválida.");
            }

        } while (!opcion.equals("0"));
    }

    private void mostrarMenu() {
        System.out.println("\n======== GESTIÓN DE USUARIOS ========");
        System.out.println("1) Crear usuario (A + credencial B)");
        System.out.println("2) Buscar usuario por ID");
        System.out.println("3) Listar usuarios");
        System.out.println("4) Actualizar usuario");
        System.out.println("5) Eliminar (baja lógica)");
        System.out.println("6) Restaurar usuario");
        System.out.println("7) Buscar por EMAIL (campo relevante)");
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
            String password = sc.nextLine();

            // Crear credencial (B)
            CredencialAcceso cred = new CredencialAcceso();
            cred.setHashPassword(password);
            cred.setSalt("SALT_FAKE");
            cred.setLastChange(LocalDateTime.now());
            cred.setRequireReset(false);

            // Crear usuario (A)
            Usuario u = new Usuario();
            u.setUsername(username);
            u.setEmail(email);
            u.setActivo(true);
            u.setFechaRegistro(LocalDateTime.now());
            u.setCredencial(cred);

            usuarioService.crearUsuarioConCredencial(u);

            System.out.println("Usuario creado con éxito.");

        } catch (Exception e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
        }
    }

    // BUSCAR POR ID
    private void buscarPorId() {
        try {
            System.out.print("Ingrese ID: ");
            Long id = Long.parseLong(sc.nextLine());

            Usuario u = usuarioService.buscarPorId(id);
            if (u == null) {
                System.out.println("❌ No existe usuario con ese ID.");
            } else {
                System.out.println("✔ Usuario encontrado:");
                System.out.println(u);
                System.out.println("Credencial: " + u.getCredencial());
            }

        } catch (NumberFormatException e) {
            System.out.println("⚠ ID inválido.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // LISTAR
    private void listarUsuarios() {
        try {
            var lista = usuarioService.listar();
            if (lista.isEmpty()) {
                System.out.println("⚠ No hay usuarios disponibles.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar: " + e.getMessage());
        }
    }

    // ACTUALIZAR
    private void actualizarUsuario() {
        try {
            System.out.print("ID a actualizar: ");
            Long id = Long.parseLong(sc.nextLine());

            Usuario u = usuarioService.buscarPorId(id);
            if (u == null) {
                System.out.println("❌ No existe ese usuario.");
                return;
            }

            System.out.println("Nuevo email:");
            String email = sc.nextLine();

            System.out.println("¿Activo? (S/N):");
            boolean activo = sc.nextLine().trim().equalsIgnoreCase("S");

            u.setEmail(email);
            u.setActivo(activo);

            usuarioService.actualizar(u);

            System.out.println("✔ Usuario actualizado.");

        } catch (Exception e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
        }
    }

    // ✔ ELIMINAR (baja lógica)
    private void eliminarUsuario() {
        try {
            System.out.print("ID a eliminar: ");
            Long id = Long.parseLong(sc.nextLine());

            usuarioService.eliminar(id);

            System.out.println("✔ Usuario eliminado lógicamente.");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // RESTAURAR
    private void restaurarUsuario() {
        try {
            System.out.print("ID a restaurar: ");
            Long id = Long.parseLong(sc.nextLine());

            usuarioService.restaurar(id);

            System.out.println("✔ Usuario restaurado.");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // BÚSQUEDA POR CAMPO RELEVANTE (EMAIL)
    private void buscarPorEmail() {
        try {
            System.out.print("Ingrese email a buscar: ");
            String email = sc.nextLine();

            var resultado = usuarioService.buscarPorEmail(email);

            if (resultado == null) {
                System.out.println("❌ No se encontró usuario con ese email.");
            } else {
                System.out.println("✔ Usuario encontrado:");
                System.out.println(resultado);
            }

        } catch (Exception e) {
            System.out.println("❌ Error en búsqueda: " + e.getMessage());
        }
    }  
    
}
