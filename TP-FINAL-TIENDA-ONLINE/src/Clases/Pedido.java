package Clases;

import Enums.EstadoPedido;

import java.util.List;

public class Pedido {

    /**
     * la clase pedido son los datos que se van a guardar de una compra que luego se van a guardar en un historiar
     * depedidos para la comodidad del cliente , esta guarda el id de pedido , el nombre del cliente , el estado del
     * pedido y una lista de los productos que se compraron
     */

    private int IDpedido = 1;
    private String NombreCliente;
    private EstadoPedido estadoPedido;
    private List<Producto> Lista_productos;

    ///-- CONSTRUCTOR --
    public Pedido( String nombreCliente, List<Producto> lista_productos) {
        NombreCliente = nombreCliente;
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

    ///-- toSTRING --
    @Override
    public String toString() {
        return "Clases.Pedido{" +
                "IDpedido=" + IDpedido +
                ", NombreCliente='" + NombreCliente + '\'' +
                ", estadoPedido=" + estadoPedido +
                ", Lista_productos=" + Lista_productos +
                '}';
    }
}
