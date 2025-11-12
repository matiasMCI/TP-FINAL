import java.util.List;

public class Pedido {

    private int IDpedido = 1;
    private String NombreCliente;
    private EstadoPedido estadoPedido;
    private List<Producto> Lista_productos;

    ///-- CONSTRUCTOR --
    public Pedido(int IDpedido, String nombreCliente, EstadoPedido estadoPedido, List<Producto> lista_productos) {
        this.IDpedido = IDpedido;
        NombreCliente = nombreCliente;
        this.estadoPedido = estadoPedido;
        Lista_productos = lista_productos;
    }

    ///-- GETTERS SETTERS --

    public int getIDpedido() {
        return IDpedido;
    }

    public void setIDpedido(int IDpedido) {
        this.IDpedido = IDpedido;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public List<Producto> getLista_productos() {
        return Lista_productos;
    }

    public void setLista_productos(List<Producto> lista_productos) {
        Lista_productos = lista_productos;
    }
}
