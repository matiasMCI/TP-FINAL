package Clases;

import Enums.EstadoPedido;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pedido {

    /**
     * la clase pedido son los datos que se van a guardar de una compra que luego se van a guardar en un historiar
     * depedidos para la comodidad del cliente , esta guarda el id de pedido , el nombre del cliente , el estado del
     * pedido y una lista de los productos que se compraron
     */

    private static int contador = 1;

    private String idPedido;
    private Map<String, ItemCarrito> items;
    private double montoTotal;
    private String idCliente;
    private String fecha;
    private EstadoPedido estado;


    ///-- CONSTRUCTOR --
    public Pedido( String idCliente, Map<String ,ItemCarrito>items) {
       this.idPedido = generarID();
        this.idCliente = idCliente;
        this.items = new HashMap<>(items);
        this.montoTotal = calcularTotal();
        this.fecha = LocalDate.now().toString();
        this.estado = EstadoPedido.PAGADO;
    }


    ///-- GETTERS SETTERS --
    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Pedido.contador = contador;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Map<String, ItemCarrito> getItems() {
        return items;
    }

    public void setItems(Map<String, ItemCarrito> items) {
        this.items = items;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }



    private String generarID(){
        return "PED"+ contador++;
    }
    private double calcularTotal(){
        double total = 0;
        for(ItemCarrito item : items.values()){
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }

    ///-- toSTRING --
    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido='" + idPedido + '\'' +
                ", items=" + items +
                ", montoTotal=" + montoTotal +
                ", idCliente='" + idCliente + '\'' +
                ", fecha='" + fecha + '\'' +
                ", estado=" + estado +
                '}';
    }
}
