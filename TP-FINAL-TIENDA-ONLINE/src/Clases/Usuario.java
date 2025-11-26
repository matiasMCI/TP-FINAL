package Clases;
/**
*
* La clase abstracta Usuario sirve como base en la creacion de
 * clases CLientes y Administradores
*
*
 */



public abstract class Usuario {
        private static int contador = 1;

        private String idUsuario;
        private String nombre;
        private String email;
        private  String contrasena;

            ///-- CONSTRUCTOR --
            public Usuario(String nombre, String email, String contrasena) {
                this.idUsuario = idGenerador();
                this.nombre = nombre;
                this.email = email;
                this.contrasena = contrasena;
            }

    /// CONSTRUCTOR PARA JSON
    public Usuario(String IDUsuario, String nombre, String email, String contrasena){
        this.idUsuario = IDUsuario;
        this.nombre = nombre;
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
    private String idGenerador(){
                return "ID" + contador++;
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

    ///-- toSTRING --
    @Override
    public String toString() {
        return ", id='" + idUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'';
    }
}




