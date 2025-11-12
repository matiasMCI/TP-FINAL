public class Comprobante {

    private String idComprobante;
    private int idCliente;
    private String fecha;
    private double montoTotal;
    private double descuento;

    ///-- CONSTRUCTOR --
    public Comprobante(String idComprobante, int idCliente, String fecha, double montoTotal, double descuento) {
        this.idComprobante = idComprobante;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.descuento = descuento;
    }
///-- GETTERS SETTERS --
    public String getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(String idComprobante) {
        this.idComprobante = idComprobante;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    ///-- METODOS --


}
