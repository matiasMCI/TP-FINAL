package Interfaces;

import org.json.JSONArray;
import sistema.SistemaTienda;
import Enums.CategoriaProducto;

import java.util.Scanner;
/**
 * Interfaz que define las acciones que puede realizar un administrador
 * dentro del sistema de la tienda online.
 */
public interface IAdministrador {

    /** Agrega un nuevo cliente al sistema. */
    void agregarCliente(SistemaTienda sistema);

    /** Da de baja a un cliente según su ID. */
    void darDeBajaCliente(SistemaTienda sistema, String id);

    /** Da de alta a un cliente según su ID. */
    void darDeAltaCliente(SistemaTienda sistema, String id);

    /** Cambia el estado activo/inactivo de un cliente. */
    void cambiarEstadoCliente(SistemaTienda sistema, String id, boolean activo);

    /** Agrega un nuevo producto al sistema. */
    void agregarProducto(SistemaTienda sistema);

    /** Modifica los datos de un producto según su ID. */
    void modificarProducto(SistemaTienda sistema, String id);

    /** Agrega stock a un producto existente. */
    void agregarStock(SistemaTienda sistema, String id, int cantidad);

    /** Quita stock de un producto existente. */
    void quitarStock(SistemaTienda sistema, String id, int cantidad);

    /** Modifica los datos de un cliente según su ID. */
    void modificarCliente(SistemaTienda sistema, String idCliente);

    /** Busca el cliente que más compras ha realizado. */
    void buscarClienteMasFrecuente(SistemaTienda sistema);

    /** Cambia el estado de un pedido según su ID. */
    void cambiarEstadoPedido(SistemaTienda sistema, String idPedido, Scanner sc);

    /** Lista los pedidos filtrados por estado. */
    void listarPedidosPorEstado(SistemaTienda sistema, Scanner sc);

    /** Lista los pedidos de un cliente específico. */
    void listarPedidosPorCliente(SistemaTienda sistema, String idCliente);


}
