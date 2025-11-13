package Interfaces;

public interface ICliente {
    public void AgregarAlCarrito(Carrito producto);
    public void EliminarDelCarrito(String string);
    public void ModificarPerfil(int opcion , int id);
    public void EstablecerDomicilioDeEntrega(double nuevoDomicilio , int id);
    public void HistorialDeCompra();
    public void RealizarCompra(int id);
    public double ConfirmarPago(double precioTotal , Cliente c);
    public void EstadoPedido();
    public void DevolverProducto();
    public double mostrarCarrito();
    public void crearPedido ();
    public void verListaDePedidos();

}
