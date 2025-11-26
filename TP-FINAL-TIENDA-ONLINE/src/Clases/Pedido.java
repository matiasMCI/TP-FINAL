package Clases;

import Enums.EstadoPedido;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Clase Pedido.
 *
 * Representa una compra realizada por un cliente dentro de la tienda online.
 * Cada pedido contiene un identificador único, el cliente que lo realizó,
 * una lista de items comprados (ItemCarrito), el monto total, la fecha de creación
 * y el estado del pedido (PAGADO, EN_PROCESO, CANCELADO, etc.).
 *
 * La clase también gestiona un contador estático para generar IDs únicos automáticamente.
 */
public class Pedido {

    private static int contador = 1;

    private String idPedido;
    private Map<String, ItemCarrito> items;
    private double montoTotal;
    private String idCliente;
    private String fecha;
    private EstadoPedido estado;


    /**
     * Constructor principal.
     * Crea un pedido nuevo a partir de un cliente y los items seleccionados.
     * Genera un ID único automáticamente, calcula el monto total y establece la fecha actual.
     *
     * @param idCliente ID del cliente que realiza el pedido.
     * @param items Mapa de items (producto y cantidad) incluidos en el pedido.
     */
    public Pedido( String idCliente, Map<String ,ItemCarrito>items) {
       this.idPedido = generarID();
        this.idCliente = idCliente;
        this.items = new HashMap<>(items);
        this.montoTotal = calcularTotal();
        this.fecha = LocalDate.now().toString();
        this.estado = EstadoPedido.PAGADO;
    }

    /**
     * Constructor utilizado para deserialización desde JSON.
     * Permite crear un objeto Pedido con todos los campos ya definidos.
     *
     * @param idPedido ID del pedido.
     * @param montoTotal Monto total del pedido.
     * @param idCliente ID del cliente.
     * @param fecha Fecha del pedido.
     * @param estado Estado del pedido.
     */
    public Pedido(String idPedido, double montoTotal, String idCliente, String fecha, EstadoPedido estado){
        this.idPedido = idPedido;
        this.montoTotal = montoTotal;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.estado = estado;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }


    /**
     * Genera un ID único para el pedido.
     *
     * @return ID en formato "PED" + contador.
     */
    private String generarID(){
        return "PED"+ contador++;
    }


    /**
     * Calcula el total del pedido sumando los subtotales de todos los items.
     *
     * @return Monto total del pedido.
     */
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
