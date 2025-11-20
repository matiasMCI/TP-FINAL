package Clases;

public class Admin extends Usuario {

    /**
     * Esta clase representa a los usuarios que van a poder controlar los clientes y productos
     * de la pagina / app de ventas del supermercado , es el rol que tiene acceso a metodos mas especificos
     * y importantes del sistema , como agregar productos o eliminar clientes
     */



    private String nombre;
    private int edad;
    private int idAdmin;

    /// CONSTRUCTOR NORMAL
    public Admin(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena);
        this.nombre = nombre;
        this.edad = edad;
        this.idAdmin = idAdmin;
    }
    /// CONSTRUCTO JSON
    public Admin(String IDUsuario, String nombre, String email, String contrasena){
        super(IDUsuario,nombre, email, contrasena);
    }

    ///-- GETTERS SETTERS --

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    ///-- toSTRING --
    @Override
    public String toString() {
        return "Clases.Administrador{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", idAdmin=" + idAdmin +
                '}';
    }

    ///-- METODOS --





}
