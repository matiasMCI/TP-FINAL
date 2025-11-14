package Interfaces;

import Clases.*;
import sistema.SistemaTienda;

public interface ICliente {
    public void AgregarAlCarrito(Producto producto);
    public void EliminarDelCarrito(String string);
    public void ModificarPerfil(SistemaTienda sistema, int opcion , int id);
    public void EstablecerDomicilioDeEntrega(SistemaTienda sistema,double nuevoDomicilio , int id);
    public void AgregarHistorialDeCompra(Pedido pedido1);
    public void RealizarCompra(SistemaTienda sistema,int id);
    public double ConfirmarPago(SistemaTienda sistema , double precioTotal , Cliente c);
    public double mostrarCarrito();
    public void crearPedido (SistemaTienda sistema , Cliente c);
    public void verListaDePedidos(SistemaTienda sistema);
    public void HistorialDeCompra();

}
