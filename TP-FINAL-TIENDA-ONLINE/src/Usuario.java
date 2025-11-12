public abstract class Usuario {
private static int contador = 1;
private int IDusuario;

private String Email;

private  String Contrasena;

    ///-- CONSTRUCTOR --
    public Usuario( String email, String contrasena) {

        this.IDusuario = contador++;
        this.Email = email;
        this.Contrasena = contrasena;


    }

    ///-- GETTERS SETTERS --
    public int getIDusuario() {
        return IDusuario;
    }

    public void setIDusuario(int IDusuario) {
        this.IDusuario = IDusuario;
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
        return "Usuario{" +
                "IDusuario=" + IDusuario +
                ", Email='" + Email + '\'' +
                ", Contrasena='" + Contrasena + '\'' +
                '}';
    }
}




