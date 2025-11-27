package Clases;


/**
 * Clase abstracta que representa un usuario dentro del sistema.
 *
 * <p>Sirve como base para la creación de objetos de tipo Cliente y Administrador.</p>
 *
 * <p>Un usuario posee información básica como nombre, apellido, email,
 * contraseña e ID único generado automáticamente.</p>
 */
public abstract class Usuario {
    /** Contador estático para generar IDs únicos. */
        private static int contador = 1;

        private String idUsuario;
        private String nombre;
        private String apellido;
        private String email;
        private  String contrasena;

        /**
         * Constructor principal utilizado para crear un usuario nuevo.
         * El ID se genera automáticamente.
         *
         * @param nombre Nombre del usuario.
         * @param apellido Apellido del usuario.
         * @param email Correo electrónico del usuario.
         * @param contrasena Contraseña del usuario.
         */

            public Usuario(String nombre, String apellido,String email, String contrasena) {
                this.idUsuario = idGenerador();
                this.nombre = nombre;
                this.apellido = apellido;
                this.email = email;
                this.contrasena = contrasena;
            }

    /**
     * Constructor utilizado al cargar datos desde JSON, donde el ID ya existe.
     *
     * @param IDUsuario Identificador único.
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param email Correo electrónico.
     * @param contrasena Contraseña del usuario.
     */
    public Usuario(String IDUsuario, String nombre, String apellido,String email, String contrasena){
        this.idUsuario = IDUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
    }

    ///-- GETTERS SETTERS --

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Usuario.contador = contador;
    }

    public void setIDusuario(String IDusuario) {
        this.idUsuario = IDusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Genera un ID único en formato ID###.
     *
     * @return ID generado.
     */
    private String idGenerador(){
                return String.format("ID%03d", contador++);
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return Nombre + apellido juntos.
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return
                "idUsuario='" + idUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'';
    }
}




