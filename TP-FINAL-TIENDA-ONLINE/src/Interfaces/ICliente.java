package Interfaces;

import Clases.*;
import sistema.SistemaTienda;
/**
 * Interfaz que define las acciones que puede realizar un cliente
 * dentro del sistema de la tienda online.
 */
public interface ICliente {

    /** Muestra la información del perfil del cliente. */
    void mostrarPerfil();

    /** Agrega fondos a la cuenta del cliente. */
    void agregarFondos(double fondos);

    /** Elimina un producto del carrito según su ID. */
    void eliminarDeCarrito(String IDProducto);

    /** Vacía todo el carrito de  compras del cliente */
    void vaciarCarrito();

    /** Agrega un producto al carrito del cliente. */
    void agregarACarrito(SistemaTienda sistema, String IDProducto, int cantidad);

    /** Calcula el total del carrito de compras. */
    double totalCarrito();

    /** Finaliza la compra y genera el pedido correspondiente. */
    void finalizarCompra(SistemaTienda sistema);

    /** Muestra los pedidos realizados por el cliente. */
    void verPedidos();

    /** Muestra el contenido del carrito de compras. */
    void mostrarCarrito();

    /** Verifica el estado de alguna opción o acción. */
    void verificacionEstado(int eleccion);

    /** Verifica que la edad del cliente cumpla los requisitos. */
    void verificacionEdad(int edad);

    /** Permite modificar los datos del perfil del cliente. */
    void modificarPerfil(SistemaTienda sistema, Cliente cliente);


}
