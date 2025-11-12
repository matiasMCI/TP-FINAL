public class Cliente extends Usuario{


    private String Nombre;
    private String Edad;
    private TIPO_CLIENTE TipoCliente;
    private ESTADO_CLIENTE estado;
    private Carrito carrito;


    ///-- CONSTRUCTOR --
    public Cliente( String email, String contrasena, String nombre, String edad, TIPO_CLIENTE tipoCliente, ESTADO_CLIENTE estado) {
        super(email, contrasena);
        this.Nombre = nombre;
        this.Edad = edad;
        this.TipoCliente = tipoCliente;
        this.estado = estado;
    }

    ///-- GETTERS SETTERS --
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public TIPO_CLIENTE getTipoCliente() {
        return TipoCliente;
    }

    public void setTipoCliente(TIPO_CLIENTE tipoCliente) {
        TipoCliente = tipoCliente;
    }

    public ESTADO_CLIENTE getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_CLIENTE estado) {
        this.estado = estado;
    }

///-- toSTRING --

    @Override
    public String toString() {
        return "Cliente{" +
                "Nombre='" + Nombre + '\'' +
                ", Edad='" + Edad + '\'' +
                ", TipoCliente=" + TipoCliente +
                ", estado=" + estado +
                ", carrito=" + carrito +
                '}';
    }
}
