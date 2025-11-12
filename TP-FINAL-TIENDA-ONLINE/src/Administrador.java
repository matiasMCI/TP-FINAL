public class Administrador extends Usuario {

    private String nombre;
    private int edad;
    private int idAdmin;

    ///-- CONSTRUCTOR --
    public Administrador(String email, String contrasena, String nombre, int edad, int idAdmin) {
        super(email, contrasena);
        this.nombre = nombre;
        this.edad = edad;
        this.idAdmin = idAdmin;
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

    ///-- METODOS --



}
