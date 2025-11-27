package Menu;

import Clases.Admin;
import Clases.Cliente;
import Enums.EstadoPedido;
import Excepciones.*;
import Utilidades.Etiquetas;
import sistema.SistemaTienda;

import java.util.Calendar;
import java.util.Scanner;

/**
 * Clase que gestiona la interacción con el usuario a través de menús de consola.
 *
 * <p>Proporciona menús y opciones tanto para clientes como para administradores.
 * Permite iniciar sesión, registrarse, modificar perfiles, gestionar carritos y pedidos,
 * así como operaciones de administración de clientes, productos y pedidos.</p>
 */

public class Menu {


    public static final String COLOR = "\u001B[36m";
    public static final String RESET = "\u001B[0m";

    /**
     * Muestra el menú de login inicial con las opciones:
     * iniciar sesión, registrarse o salir.
     */

    public static void menuLogin(){
        System.out.println(COLOR + "─────────── TIENDA ONLINE ───────────" + RESET);
        System.out.println();
        System.out.println("   (1) Iniciar sesión");
        System.out.println("   (2) Registrarse");
        System.out.println("   (3) Salir");
        System.out.println();
        System.out.println(COLOR + "─────────────────────────────────────" + RESET);
    }



    /**
     * Muestra el menú principal del cliente según su estado de cuenta.
     *
     * @param sistema SistemaTienda utilizado para operaciones
     * @param cliente Cliente logueado
     */
    public static void menuCliente(SistemaTienda sistema, Cliente cliente){
        Scanner sc = new Scanner(System.in);
        boolean logueado = true;

        while(logueado){
            System.out.println(COLOR +"─────────    Menu Cliente   ─────────" + RESET);

            if(cliente.isEstado()){
                /// Si el cliente tiene la cuenta activa muestra este menu completo
                mostrarMenuActivo();
            }else{
                /// sino muestra este menu con menos opciones
                mostrarMenuInactivo();
            }
            int opcion = sistema.leerEnteroSeguro(sc,"Opcion: ");
            logueado = procesarOpciones(sistema,cliente,opcion);
            sistema.subirJSON();

        }
    }


    /**
     * Procesa las opciones seleccionadas por el cliente según su estado de cuenta.
     *
     * @param sistema SistemaTienda utilizado para operaciones
     * @param cliente Cliente logueado
     * @param opcion  Opción seleccionada por el usuario
     * @return {@code true} si el usuario continúa en el menú, {@code false} si cierra sesión
     */
    private static boolean procesarOpciones(SistemaTienda sistema, Cliente cliente, int opcion){

        Scanner sc = new Scanner(System.in);
        String continuar = "si";

        if(cliente.isEstado()){
            /// CUENTA ACTIVADA
            switch (opcion){
                case 1:
                    cliente.mostrarPerfil();
                    break;
                case 2:
                    double fondos = sistema.leerDoubleSeguro(sc,"Ingrese fondos: ");
                    try {
                        cliente.agregarFondos(fondos);
                    }catch (AccionImposibleEx ai){
                        System.out.println(Etiquetas.ERROR + "al asignar fondos: " + ai.getMessage());
                    }catch (FondosSuperadosEx e){
                        System.out.println(Etiquetas.ERROR + "con tope fondos: " + e.getMessage());
                    }
                    break;
                case 3:
                    while(continuar.equalsIgnoreCase("si")) {


                        sistema.mostrarListaProductosPorCategoria();
                        System.out.println("Ingrese el ID del producto a agregar al carrito: ");
                        String id = sc.nextLine();
                        int cantidad = sistema.leerEnteroSeguro(sc,"Ingrese la cantidad de producto a agregar:");
                        try {
                            cliente.agregarACarrito(sistema, id, cantidad);
                        } catch (SinStockEx e) {
                            System.out.println(Etiquetas.ERROR+" al agregar por stock: " + e.getMessage());
                        } catch (ElementoInexistenteEx EI) {
                            System.out.println(Etiquetas.ERROR+"al agregar por id: " + EI.getMessage());
                        }catch (AccionImposibleEx ai){
                            System.out.println(Etiquetas.ERROR+ "al agregar producto: " + ai.getMessage());
                        }
                        System.out.println("Desea agregar otro producto? SI/NO: ");
                        continuar = sc.nextLine();
                    }
                    break;
                case 4:
                    cliente.mostrarCarrito();
                    System.out.println("Ingresa id del producto a eliminar: ");
                    String idProducto = sc.nextLine();
                    try {
                        cliente.eliminarDeCarrito(idProducto);
                    }catch (ElementoInexistenteEx e){
                        System.out.println(Etiquetas.ERROR+" al eliminar del carrito: " + e.getMessage());
                    }
                    break;
                case 5:
                    try{
                        cliente.mostrarCarrito();
                    }catch (ListasVaciasEx e){
                        System.out.println(Etiquetas.ERROR + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        cliente.vaciarCarrito();
                    }catch (CarritoVacioEx e ){
                        System.out.println(Etiquetas.ERROR+"Accion imposible: " + e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        cliente.finalizarCompra(sistema);
                    }catch (CarritoVacioEx e){
                        System.out.println(Etiquetas.ERROR+ " con el carrito: " + e.getMessage());
                    }catch (FondosInsuficientesEx fi){
                        System.out.println(Etiquetas.ERROR+" con los fondos: " + fi.getMessage());
                    }
                    break;
                case 8:
                    try {
                        cliente.verPedidos();
                    }catch (ListasVaciasEx e){
                        System.out.println(Etiquetas.ERROR+" al mostrar historial de pedidos: " + e.getMessage());
                    }
                    break;
                case 9:
                    cliente.modificarPerfil(sistema,cliente);
                    break;
                case 10:
                    cliente.desactivar();
                    System.out.println(Etiquetas.INFO+ "Cuenta desactivada");
                    break;
                case 11:
                    System.out.println(Etiquetas.INFO + "Sesion cerrada");
                    return false;
                default:
                    System.out.println(Etiquetas.WARNING+"Opcion invalida...");
                    break;
            }

        }else{

            /// CUENTA DESACTIVADA
            switch (opcion) {
                case 1:
                    cliente.mostrarPerfil();
                    break;
                case 2:
                    System.out.println("Activando cuenta...");
                    cliente.activar();
                    System.out.println("Cuenta activada!");
                    break;
                case 3:
                    cliente.modificarPerfil(sistema,cliente);
                    break;
                case 4:
                    System.out.println(Etiquetas.INFO+"Sesion cerrada!");
                    return false;
                default:
                    System.out.println(Etiquetas.WARNING+"Opcion invalida");
                    break;
            }

        }
        return true;
    }




    /**
     * Muestra el menú de cliente cuando la cuenta está inactiva.
     */
    public static void mostrarMenuInactivo(){
        System.out.println("(1) Ver Perfil");
        System.out.println("(2) Activar Cuenta");
        System.out.println("(3) Modificar Perfil");
        System.out.println("(4) Desloguearse");
        System.out.println(COLOR + "─────────────────────────────────────" + RESET);
    }
    /**
     * Muestra el menú completo del cliente cuando la cuenta está activa.
     */
    public static void mostrarMenuActivo(){
        System.out.println("(1) Ver Perfil");
        System.out.println("(2) Agregar Fondos");
        System.out.println("(3) Agregar productos al carrito");
        System.out.println("(4) Eliminar productos del carrito");
        System.out.println("(5) Mostrar Carrito");
        System.out.println("(6) Vaciar Carrito");
        System.out.println("(7) Finalizar Compra");
        System.out.println("(8) Ver pedidos");
        System.out.println("(9) Modificar perfil");
        System.out.println("(10) Desactivar cuenta");
        System.out.println("(11) Desloguearse");
        System.out.println(COLOR + "─────────────────────────────────────" + RESET);
    }

    /**
     * Muestra y procesa el menú del administrador, permitiendo
     * agregar/modificar clientes y productos, gestionar stock y pedidos.
     *
     * @param admin Administrador que accede al sistema
     * @param sistema SistemaTienda utilizado para operaciones
     */
    public static void menuAdmin(Admin admin, SistemaTienda sistema) {

        Scanner sc = new Scanner(System.in);
        boolean continuar = true;
        String idCliente;
        String idProducto;
        int cantidad;
        String seguir = "si";

        while (continuar) {
            System.out.println(COLOR + "────────────────────   ADMIN   ────────────────────" + RESET);
            System.out.println("(1) Agregar Cliente"+ "           \"(11) Agregar stock\"");
            System.out.println("(2) Agregar Producto" + "          \"(12) Quitar stock\"");
            System.out.println("(3) Dar de alta Cliente" + "       \"(13) Mostrar Comprobantes\"");
            System.out.println("(4) Dar de baja Cliente" + "       \"(14) Mostrar Pedidos\"");
            System.out.println("(5) Dar de alta Producto" + "      \"(15) Mostrar Cliente mas Frecuente\"");
            System.out.println("(6) Dar de baja Producto" + "      \"(16) Cambiar Estado Pedido\"");
            System.out.println("(7) Modificar Cliente"+"         \"(17) Mostrar Pedidos Por ID Cliente\"");
            System.out.println("(8) Modificar Producto"  + "        \"(18) Mostrar Pedidos Por Estado\"");
            System.out.println("(9) Mostrar Clientes"+ "           \"(19) Mostrar Info Cliente por ID \"");
            System.out.println("(10) Mostrar Productos"+ "         \"(20) Cerrar Sesion\"");
            System.out.println(COLOR + "────────────────────────────────────────────────────────" + RESET);



            sistema.subirJSON();
            sistema.subirJSONProductos();

            int opcion = sistema.leerEnteroSeguro(sc,"Opcion: ");

            switch (opcion) {
                case 1:
                    admin.agregarCliente(sistema);
                    sistema.subirJSON();
                    break;
                case 2:
                    admin.agregarProducto(sistema);
                    sistema.subirJSONProductos();
                    break;
                case 3:
                    sistema.mostrarListaCliente();
                    System.out.println("Ingrese la id cliente a dar de alta: ");
                    idCliente = sc.nextLine().trim();
                    try {
                        admin.darDeAltaCliente(sistema,idCliente);
                    } catch (ElementoInexistenteEx e) {
                        System.out.println(Etiquetas.ERROR + " al dar de alta cliente: " + e.getMessage());
                    }
                    break;
                case 4:
                    sistema.mostrarListaCliente();
                    System.out.println("Ingrese la id cliente a dar de baja: ");
                    idCliente = sc.nextLine().trim();
                    try {
                        admin.darDeBajaCliente(sistema,idCliente);
                    } catch (ElementoInexistenteEx e) {
                        System.out.println(Etiquetas.ERROR + "al dar de baja cliente: " + e.getMessage());
                    }
                    break;
                case 5:
                    sistema.mostrarListaProducto();
                    System.out.println("Ingrese la id producto a dar de alta: ");
                    idProducto = sc.nextLine().trim();
                    try{
                        admin.darDeAltaProducto(sistema,idProducto);
                    }catch (ElementoInexistenteEx e){
                        System.out.println(Etiquetas.ERROR + " al dar de alta producto: " + e.getMessage());
                  }
                    break;
                case 6:
                    sistema.mostrarListaProducto();
                    System.out.println("Ingrese la id producto a dar de baja: ");
                    idProducto = sc.nextLine().trim();
                    try{
                        admin.darDeBajaProducto(sistema,idProducto);
                    }catch (ElementoInexistenteEx e){
                        System.out.println(Etiquetas.ERROR + " al dar de baja producto: " + e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        sistema.mostrarListaCliente();
                        System.out.println("Ingrese la id Cliente a modificar: ");
                        idCliente = sc.nextLine().trim();
                        admin.modificarCliente(sistema, idCliente);
                    } catch (ElementoInexistenteEx e) {
                        System.out.println(Etiquetas.ERROR + "al modificar cliente: " + e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        sistema.mostrarListaProducto();
                        System.out.println("Ingrese ID del producto a modificar: ");
                        idProducto = sc.nextLine().trim();
                        admin.modificarProducto(sistema,idProducto);
                    } catch (ElementoInexistenteEx e) {
                        System.out.println(Etiquetas.ERROR + " al modificar producto: " + e.getMessage());
                    }
                    sistema.subirJSONProductos();
                    break;
                case 9:
                    sistema.mostrarListaCliente();
                    break;
                case 10:
                    sistema.mostrarListaProducto();
                    break;
                case 11:
                    while (seguir.equalsIgnoreCase("si")) {
                        sistema.mostrarListaProducto();
                        System.out.println("Ingrese la idProducto a agregar stock");
                        idProducto = sc.nextLine().trim();
                        cantidad = sistema.leerEnteroSeguro(sc, "Ingrese la cantidad a agregar: ");
                        try {
                            admin.agregarStock(sistema,idProducto, cantidad);
                        } catch (ElementoInexistenteEx e) {
                            System.out.println(Etiquetas.ERROR + "al agregar stock: " + e.getMessage());
                        }
                        System.out.println("Desea agregar stock a otro producto? SI/NO: ");
                        seguir = sc.nextLine();
                    }
                    break;
                case 12:
                    while (seguir.equalsIgnoreCase("si")) {
                        sistema.mostrarListaProducto();
                        System.out.println("Ingrese la idProducto a quitar stock: ");
                        idProducto = sc.nextLine().trim();
                        cantidad = sistema.leerEnteroSeguro(sc,"Ingrese la cantidad a descontar: ");
                        try {
                            admin.quitarStock(sistema,idProducto, cantidad);
                        } catch (ElementoInexistenteEx e) {
                            System.out.println(Etiquetas.ERROR+"al descontar stock: " + e.getMessage());
                        }
                        System.out.println("Desea agregar stock a otro producto? SI/NO: ");
                        seguir = sc.nextLine();
                    }
                    break;
                case 13:
                    try{
                        sistema.mostrarComprobantes();
                    }catch (ListasVaciasEx e){
                        System.out.println(Etiquetas.ERROR+" al mostrar comprobantes: " + e.getMessage());
                    }
                    break;
                case 14:
                    try {
                        sistema.mostrarListaPedido();
                    } catch (ListasVaciasEx e) {
                        System.out.println(Etiquetas.ERROR + "al mostrar pedidos: " + e.getMessage());
                    }
                    break;
                case 15:
                        try{
                            admin.buscarClienteMasFrecuente(sistema);
                        }catch (ListasVaciasEx e){
                            System.out.println(Etiquetas.ERROR + "al buscar clientes: " + e.getMessage());
                        }
                     break;
                case 16:
                    sistema.mostrarListaPedido();
                    System.out.println("Ingrese id pedido a cambiar estado: ");
                    String idPedido = sc.nextLine();
                    try{
                        admin.cambiarEstadoPedido(sistema, idPedido, sc);
                    }catch (ElementoInexistenteEx e){
                        System.out.println(Etiquetas.ERROR + " al cambiar estado" + e.getMessage());
                    }catch (AccionImposibleEx ai){
                        System.out.println(Etiquetas.ERROR + " al cambiar estado " + ai.getMessage());
                    }
                    break;
                case 17:
                    sistema.mostrarListaCliente();
                    System.out.println("Ingrese la id del cliente: ");
                    idCliente = sc.nextLine();
                    try{
                        admin.listarPedidosPorCliente(sistema,idCliente );
                    }catch (ElementoInexistenteEx e){
                        System.out.println(Etiquetas.ERROR + " con la ID: " + e.getMessage());
                    }catch (ListasVaciasEx lv){
                        System.out.println(Etiquetas.INFO + "historial Pedidos: " + lv.getMessage());
                    }
                    break;
                case 18:
                    admin.listarPedidosPorEstado(sistema, sc);
                    break;
                case 19:
                    try{
                        System.out.println("Ingrese la id Cliente a mostrar Info: ");
                        idCliente = sc.next().trim();
                        admin.mostrarClientePorID(sistema, idCliente);
                    }catch (ElementoInexistenteEx e){
                        System.out.println(Etiquetas.ERROR + " al mostrar perfil: " + e.getMessage());
                    }
                    break;
                case 20:
                    continuar = false;
                    System.out.println(Etiquetas.INFO+"Cerrando sesion...");
                    break;
                default:
                    System.out.println(Etiquetas.WARNING+"Opcion invalida...");
                    break;
            }

        }
    }

}
