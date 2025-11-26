package Clases;
/**
 * Clase Comprobante.
 *
 * Representa un comprobante de compra generado para un pedido realizado por un cliente.
 * Cada comprobante contiene información sobre el pedido, el cliente, la fecha,
 * el total pagado y el archivo donde se guardó la información.
 *
 * Se utiliza principalmente para registrar y mostrar el detalle de un pedido
 * después de que este ha sido completado.
 */
public class Comprobante {
    private String idPedido;
    private String idCliente;
    private String fecha;
    private double total;
    private String archivoGenerado;

    /**
     * Constructor principal de la clase Comprobante.
     *
     * @param idPedido ID del pedido.
     * @param idCliente ID del cliente que realizó el pedido.
     * @param fecha Fecha de generación del comprobante.
     * @param total Monto total del pedido.
     * @param archivoGenerado Nombre del archivo donde se guarda el comprobante.
     */
    public Comprobante(String idPedido, String idCliente, String fecha, double total, String archivoGenerado) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.total = total;
        this.archivoGenerado = archivoGenerado;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public String getArchivoGenerado() {
        return archivoGenerado;
    }

    @Override
    public String toString() {
        return "Comprobante{" +
                "idPedido='" + idPedido + '\'' +
                ", idCliente='" + idCliente + '\'' +
                ", fecha='" + fecha + '\'' +
                ", total=" + total +
                ", archivoGenerado='" + archivoGenerado + '\'' +
                '}';
    }
}
