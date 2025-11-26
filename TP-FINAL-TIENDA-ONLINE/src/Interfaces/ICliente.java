package Interfaces;

import Clases.*;
import sistema.SistemaTienda;

public interface ICliente {

    public void mostrarPerfil();
    public void agregarFondos(double fondos);
    public void eliminarDeCarrito(String IDProducto);
    public void vaciarCarrito();
    public void agregarACarrito(SistemaTienda sistema, String IDProducto, int cantidad);
    public double totalCarrito();
    public void finalizarCompra(SistemaTienda sistema);
    public void verPedidos();
    public void mostrarCarrito();
    public void verificacionEstado(int eleccion);
    public void verificacionEdad(int edad);
    public void modificarPerfil(SistemaTienda sistema,Cliente cliente);




}
