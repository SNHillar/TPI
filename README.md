# Proyecto Integrador: Programación 2 y Base de Datos I

Este proyecto es una app en Java que gestiona usuarios y credenciales, conectándose a una base de datos MySQL.

---

## 🛠️ Cómo Levantar el Entorno (Setup)

Sigue estos pasos para poder ejecutar el proyecto en tu máquina local.

### 1. Prerrequisitos

Asegúrate de tener instalado:
* Java JDK 17 (o la versión que estén usando)
* MySQL Server 8.0 (o el que usen)
* Un IDE (IntelliJ, Eclipse, etc.)
* El driver JDBC de MySQL (incluido en el repo o link para descargarlo)

* ### 2. Base de Datos 🛢️

**IMPORTANTE:** El esquema de la base de datos se encuentra en el archivo `/sql/schema.sql`.

1.  Inicia tu servidor MySQL.
2.  Crea la base de datos: `CREATE DATABASE gestion_usuarios_2;`
3.  Selecciona la base de datos: `USE gestion_usuarios_2;`
4.  Ejecuta el script `/sql/schema.sql` para crear las tablas (`usuario`, `credencial_acceso`) con todas sus restricciones.

### 3. Configuración del Proyecto

1.  Clona este repositorio: `git clone https://github.com/SNHillar/TPI`
2.  Abre el proyecto con tu IDE.
3.  Asegúrate de que el driver JDBC de MySQL esté en el Build Path.
4.  Verifica las credenciales de conexión en `config/DatabaseConnection.java`. 

### 4. Ejecutar la Aplicación 🚀

Para probar la conexión, ejecuta la clase `TestConexion.java`.
Para iniciar la aplicación, ejecuta la clase `Main.java` (o la que sea).
