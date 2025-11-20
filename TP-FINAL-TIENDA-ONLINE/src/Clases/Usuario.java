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

        private String IDusuario;
        private String nombre;
        private String Email;
        private  String Contrasena;

            ///-- CONSTRUCTOR --
            public Usuario(String nombre, String email, String contrasena) {
                this.IDusuario = idGenerador();
                this.nombre = nombre;
                this.Email = email;
                this.Contrasena = contrasena;
            }

    ///-- GETTERS SETTERS --

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Usuario.contador = contador;
    }

    public void setIDusuario(String IDusuario) {
        this.IDusuario = IDusuario;
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

    public String getIDusuario() {
        return IDusuario;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    ///-- toSTRING --
    @Override
    public String toString() {
        return "ID='" + IDusuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", Email='" + Email + '\'';
    }
}




