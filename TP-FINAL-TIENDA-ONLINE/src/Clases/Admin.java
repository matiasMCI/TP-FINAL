package Clases;

import Enums.CategoriaProducto;
import Enums.EstadoPedido;
import Excepciones.AccionImposibleEx;
import Excepciones.ElementoInexistenteEx;
import Excepciones.ListasVaciasEx;
import Interfaces.IAdministrador;
import Utilidades.Etiquetas;
import sistema.SistemaTienda;

import java.util.Scanner;



/**
 * Representa a un administrador dentro del sistema de ventas del supermercado.
 * El administrador posee permisos especiales para gestionar clientes, productos
 * y ciertos procesos internos como el manejo de pedidos o modificaciones de stock.
 * Entre sus funciones principales se encuentran:
 * - Dar de alta o baja clientes y productos
 * - Agregar y modificar productos
 * - Agregar clientes
 * - Controlar stock
 * - Modificar datos de clientes
 * - Gestionar estados de pedidos
 * - Listar pedidos filtrados
 * - Buscar al cliente más frecuente
 * Esta clase extiende a Usuario y aplica las capacidades definidas
 * en la interfaz IAdministrador.
 */


public class Admin extends Usuario implements IAdministrador {



    /**
     * Constructor para crear un administrador nuevo mediante datos proporcionados por consola.
     *
     * @param nombre     Nombre del administrador
     * @param apellido   Apellido del administrador
     * @param email      Email utilizado para iniciar sesión
     * @param contrasena Contraseña del administrador
     */
    public Admin(String nombre, String apellido,String email, String contrasena) {
        super(nombre, apellido,email, contrasena);

    }
    /**
     * Constructor utilizado para recrear administradores desde archivos JSON.
     *
     * @param IDUsuario  ID único del administrador
     * @param nombre     Nombre del administrador
     * @param apellido   Apellido del administrador
     * @param email      Email del administrador
     * @param contrasena Contraseña del administrador
     */
    public Admin(String IDUsuario, String nombre,String apellido, String email, String contrasena){
        super(IDUsuario,nombre,apellido, email, contrasena);
    }


    ///-- toSTRING --
    @Override
    public String toString() {
        return "Admin{" + super.toString() + " }";
    }



    /**
     * Agrega un nuevo cliente al sistema solicitando los datos por consola.
     *
     * @param sistema Sistema que contiene la lista de clientes
     */
    public void agregarCliente(SistemaTienda sistema){
        Scanner sc = new Scanner(System.in);
        boolean flag = false;
        System.out.println("───────────  Agregar Nuevo Cliente  ───────────");
        System.out.println("Ingrese nombre:");
        String nombre = sc.nextLine();
        System.out.println("Ingrese apellido: ");
        String apellido = sc.nextLine();
        System.out.println("Ingrese email: ");
        String email = sc.nextLine();
        System.out.println("Ingrese contraseña: ");
        String contrasena = sc.nextLine();
        int edad;
        do {
            System.out.println("La edad debe ser entre 18 y 99 años.");
            edad = sistema.leerEnteroSeguro(sc, "Ingrese la edad: ");
            if(edad >= 18 && edad <= 99){
                flag = true;
            }
        }while(!flag);
        sistema.agregarCliente(nombre,apellido,email,contrasena,edad);
        System.out.println(Etiquetas.EXITO +"Cliente agregado!");
    }

    /**
     * Muestra el perfil de un cliente específico según su ID.
     *
     * Este método busca un cliente en el sistema mediante la ID proporcionada.
     * Si el cliente existe, se llama al método mostrarPerfil() del cliente
     * para mostrar su información. Si no existe, se lanza una excepción.
     *
     * @param sistema El sistema de tienda que contiene la lista de clientes.
     * @param id La ID del cliente que se desea mostrar.
     * @throws ElementoInexistenteEx Si no se encuentra ningún cliente con la ID proporcionada.
     */
    public void mostrarClientePorID(SistemaTienda sistema, String id)throws ElementoInexistenteEx{
        Cliente cliente = sistema.getListaCliente().getPorId(id);
        if(cliente == null){
            throw new ElementoInexistenteEx("La id del cliente no existe...");
        }
        cliente.mostrarPerfil();
    }

    /**
     * Da de baja un cliente por ID.
     *
     * @param sistema Sistema donde se aloja la lista de clientes
     * @param id      ID del cliente
     * @throws ElementoInexistenteEx Si la ID no existe
     */
    public void darDeBajaCliente(SistemaTienda sistema,String id)throws ElementoInexistenteEx{
        cambiarEstadoCliente(sistema,id,false);
    }
    /**
     * Da de alta un cliente por ID.
     */
    public void darDeAltaCliente(SistemaTienda sistema,String id)throws  ElementoInexistenteEx{
        cambiarEstadoCliente(sistema, id,true);
    }

    /**
     * Cambia el estado (activo/inactivo) de un cliente.
     *
     * @param sistema Sistema con la lista de clientes
     * @param id      ID del cliente
     * @param activo  true = activar, false = desactivar
     * @throws ElementoInexistenteEx Si el cliente no existe
     */
    public void cambiarEstadoCliente(SistemaTienda sistema, String id, boolean activo)throws ElementoInexistenteEx{
        Cliente cliente = sistema.getListaCliente().getPorId(id);
        if(cliente == null){
            throw new ElementoInexistenteEx("La id del cliente no existe...");
        }
        if(activo){
            cliente.activar();
            System.out.println(Etiquetas.EXITO +"Cliente dado de alta!");
        }else{
            cliente.desactivar();
            System.out.println(Etiquetas.EXITO +"Cliente dado de baja!");
        }
    }




    /**
     * Da de baja un producto por ID.
     *
     * @throws ElementoInexistenteEx Si el ID no existe
     */
    public void darDeBajaProducto(SistemaTienda sistema, String id)throws  ElementoInexistenteEx{
        cambiarEstadoProducto(sistema,id,false);
    }

    /**
     * Da de alta un producto por ID.
     */
    public void darDeAltaProducto(SistemaTienda sistema, String id)throws  ElementoInexistenteEx{
        cambiarEstadoProducto(sistema,id,true);
    }
    /**
     * Cambia el estado (activo/inactivo) de un producto.
     *
     * @throws ElementoInexistenteEx Si el producto no existe
     */
    public void cambiarEstadoProducto(SistemaTienda sistema, String id, boolean activo) throws ElementoInexistenteEx{
        Producto producto = sistema.getListaProductos().getPorId(id);
        if(producto == null){
            throw new ElementoInexistenteEx("La id Producto no existe...");
        }
        if(activo){
            producto.activar();
            System.out.println(Etiquetas.EXITO +"Producto dado de alta!");
        }else{
            producto.desactivar();
            System.out.println(Etiquetas.EXITO +"Producto dado de baja!");
        }
    }
    /**
     * Agrega un nuevo producto al sistema consultando datos desde consola.
     *
     * @param sistema Sistema principal
     */
    public void agregarProducto(SistemaTienda sistema){

        Scanner sc = new Scanner(System.in);
        System.out.println("───────────  Agregar Nuevo Producto  ───────────");
        System.out.println("Ingrese nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Ingrese precio: ");
        double precio = sistema.leerDoubleSeguro(sc, "Ingrese precio: ");

        CategoriaProducto categoriaProducto;

        do {
            System.out.println("Categorias disponibles: COMIDA, BEBIDAS, ALIMENTOS, POSTRES");
            System.out.println("Ingrese categoria: ");
            String categoriaString = sc.nextLine();
            try {
                categoriaProducto = CategoriaProducto.valueOf(categoriaString.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println(Etiquetas.INFO+"Categoria invalida.");
                categoriaProducto = null;
            }
        }while(categoriaProducto == null);
        System.out.println("Ingrese descripcion: ");
        String descripcion = sc.nextLine();
        int stock = sistema.leerEnteroSeguro(sc, "Ingrese stock: ");

        sistema.agregarProducto(nombre,precio,categoriaProducto,descripcion,stock);
        System.out.println(Etiquetas.EXITO +"Producto Agregado con exito!");
    }


    /**
     * Permite modificar todos los atributos editables de un producto ya existente.
     *
     * @throws ElementoInexistenteEx Si el ID no existe
     */
    public void modificarProducto(SistemaTienda sistema,String id)throws ElementoInexistenteEx {
        if(!sistema.getListaProductos().existeId(id)){
            throw new ElementoInexistenteEx("La id del producto no existe...");
        }
        Producto productoModificar = sistema.getListaProductos().getPorId(id);
        Scanner sc = new Scanner(System.in);
        boolean terminar = true;

        System.out.println("─────────── Modificar Producto ───────────");

        while(terminar){
            System.out.println("1. Cambiar Nombre");
            System.out.println("2. Cambiar Precio");
            System.out.println("3. Cambiar Categoria");
            System.out.println("4. Cambiar Descripcion");
            System.out.println("5. Cambiar stock");
            System.out.println("6. Terminar");
            int opcion = sistema.leerEnteroSeguro(sc, "Opcion: ");

            switch (opcion){
                case 1:
                    System.out.println("Ingrese nombre: ");
                    String nombre = sc.nextLine();
                    productoModificar.setNombreProducto(nombre);
                    break;
                case 2:
                    double precio = sistema.leerDoubleSeguro(sc, "Ingrese Precio: ");
                    productoModificar.setPrecio(precio);
                    break;
                case 3:

                    CategoriaProducto categoriaProducto;

                    do {
                        System.out.println("Categorias disponibles: COMIDA, BEBIDAS, ALIMENTOS, POSTRES");
                        System.out.println("Ingrese categoria: ");
                        String categoriaString = sc.nextLine();
                        try {
                            categoriaProducto = CategoriaProducto.valueOf(categoriaString.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println(Etiquetas.INFO+"Categoria invalida.");
                            categoriaProducto = null;
                        }
                    }while(categoriaProducto == null);
                    productoModificar.setCategoriaProducto(categoriaProducto);
                    break;
                case 4:
                    System.out.println("Ingrese descripcion: ");
                    String descripcion = sc.nextLine();
                    productoModificar.setDescripcion(descripcion);
                    break;
                case 5:
                    int stock = sistema.leerEnteroSeguro(sc, "Ingrese stock: ");
                    productoModificar.setStock(stock);
                    break;
                case 6:
                    terminar = false;
                    System.out.println(Etiquetas.INFO+"Aplicando cambios");
                    break;
                default:
                    System.out.println(Etiquetas.INFO+"Opcion invalida");
                    break;
            }

        }
        System.out.println(Etiquetas.EXITO +"Cambios exitosos!");
    }




    /**
     * Agrega stock a un producto.
     *
     * @throws ElementoInexistenteEx Si el ID no existe
     */
    public void agregarStock(SistemaTienda sistema,String  id, int cantidad)throws ElementoInexistenteEx{
        Producto producto = sistema.getListaProductos().getPorId(id);
        if(producto == null){
            throw new ElementoInexistenteEx("El producto no existe...");
        }
        producto.setStock(producto.getStock() + cantidad);
        System.out.println(Etiquetas.EXITO +"Stock agregado con exito!");
    }

    /**
     * Quita stock a un producto. Si el resultado es negativo, lo deja en 0.
     */
    public void quitarStock(SistemaTienda sistema,String id, int cantidad)throws ElementoInexistenteEx{
        Producto producto = sistema.getListaProductos().getPorId(id);
        if(producto == null){
            throw new ElementoInexistenteEx("El producto no existe...");
        }
        /**
         * Si la resta da menor a 0, seteamos en 0 el stock. para que el stock no sea negativo
         */
        if((producto.getStock() - cantidad) < 0){
            producto.setStock(0);
        }
        producto.setStock(producto.getStock() - cantidad);
        System.out.println(Etiquetas.EXITO +"Stock descontado con exito!");
    }




    /**
     * Permite modificar cualquier dato editable de un cliente.
     *
     * @throws ElementoInexistenteEx Si no existe el ID
     */

    public void modificarCliente(SistemaTienda sistema, String idCliente)throws ElementoInexistenteEx {
        if(!sistema.getListaCliente().getListaMapGenerica().containsKey(idCliente)){
            throw new ElementoInexistenteEx("No existe esa id...");
        }
        Cliente cliente = sistema.getListaCliente().getPorId(idCliente);
        Scanner sc = new Scanner(System.in);
        boolean confimar = true;
        System.out.println("───────────  Modificacion de Cliente  ───────────");
        do{
            System.out.println("\n1. Cambiar nombre");
            System.out.println("2.Cambiar apellido");
            System.out.println("3. Cambiar email");
            System.out.println("4. Cambiar contraseña");
            System.out.println("5. Cambiar edad ");
            System.out.println("6. Cambiar estado");
            System.out.println("7. Terminar");
            int opcion = sistema.leerEnteroSeguro(sc,"Opcion: ");
            switch (opcion){
                case 1:
                    System.out.println("Ingrese nuevo nombre: ");
                    String nombre = sc.nextLine();
                    cliente.setNombre(nombre);
                    break;
                case 2:
                    System.out.println("Ingrese nuevo apellido");
                    String apellido = sc.nextLine();
                    cliente.setApellido(apellido);
                    break;
                case 3:
                    System.out.println("Ingrese nuevo email: ");
                    String email = sc.nextLine();
                    cliente.setEmail(email);
                    break;
                case 4:
                    System.out.println("Ingrese nueva contraseña: ");
                    String contrasena = sc.nextLine();
                    cliente.setContrasena(contrasena);
                    break;
                case 5:
                    int edad = sistema.leerEnteroSeguro(sc,"Ingrese nueva edad: ");
                    try {
                        cliente.verificacionEdad(edad);
                    }catch (AccionImposibleEx e) {
                        System.out.println(Etiquetas.ERROR + "error al asignar edad: " + e.getMessage());
                    }
                    break;
                case 6:
                    int estadoEleccion = sistema.leerEnteroSeguro(sc,"Estado: (1)Activar, (2) Desactivar: ");
                    try{
                        cliente.verificacionEstado(estadoEleccion);
                    }catch (AccionImposibleEx e){
                        System.out.println(Etiquetas.ERROR + "al asignar estado: " + e.getMessage());
                    }
                    break;
                case 7:
                    confimar = false;
                    System.out.println(Etiquetas.EXITO +"Cambio aplicados con exito!");
                    break;
                default:
                    System.out.println(Etiquetas.WARNING+"Opcion invalida");
                    break;
            }
        }while(confimar);
    }


    /**
     * Busca al cliente que más pedidos realizó.
     *
     * @throws ListasVaciasEx Si no hay clientes en el sistema
     */

    public void buscarClienteMasFrecuente(SistemaTienda sistema) throws ListasVaciasEx {
        if(sistema.getListaCliente().isListaVacia()){
            throw new ListasVaciasEx("No hay clientes");
        }
        Cliente masFrecuente = null;
        int maxPedidos = -1;
        for (Cliente cliente : sistema.getListaCliente().getListaMapGenerica().values()) {
            int cantidadPedidos = cliente.getHistorialPedidos().size();

            if (cantidadPedidos > maxPedidos) {
                maxPedidos = cantidadPedidos;
                masFrecuente = cliente;
            }
        }
        System.out.println("El cliente mas frecuente es, ID: " + masFrecuente.getIdUsuario() + ", nombre Completo: " + masFrecuente.getNombreCompleto() +
                ", Cant Pedidos: " + masFrecuente.getHistorialPedidos().size());
        System.out.println("─────────────────────────────────\n");
    }



    private EstadoPedido leerEstadoPedido(Scanner sc) {
        EstadoPedido estado = null;

        do {
            System.out.println("Estados disponibles: PAGADO, ENTREGADO");
            System.out.print("Ingrese Estado: ");
            String entrada = sc.nextLine().trim().toUpperCase();

            try {
                estado = EstadoPedido.valueOf(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println(Etiquetas.INFO + " Estado inválido.");
            }

        } while (estado == null);

        return estado;
    }

    /**
     * Cambia el estado de un pedido.
     *
     * @throws ElementoInexistenteEx Si el pedido no existe
     * @throws AccionImposibleEx     Si el pedido ya tiene el estado indicado
     */
    public void cambiarEstadoPedido(SistemaTienda sistema, String idPedido, Scanner sc) throws ElementoInexistenteEx,AccionImposibleEx{
            Pedido pedido = sistema.getListaPedidos().getPorId(idPedido);
            if (pedido == null) {
                throw new ElementoInexistenteEx("Pedido no encontrado: " + idPedido);
            }
            EstadoPedido estadoPedido = leerEstadoPedido(sc);

            if (pedido.getEstado() == estadoPedido) {
                throw new AccionImposibleEx("El pedido ya tiene ese estado...");
            }

            pedido.setEstado(estadoPedido);
            System.out.println(Etiquetas.EXITO + " al cambiar el estado del pedido");
            sistema.subirJSONPedidos();
    }

    /**
     * Lista todos los pedidos que coinciden con un estado específico.
     *
     * @throws ElementoInexistenteEx Si no hay pedidos en ese estado
     */
    public void listarPedidosPorEstado(SistemaTienda sistema, Scanner sc)throws ElementoInexistenteEx {

        EstadoPedido estadoPedido = leerEstadoPedido(sc);
        boolean encontrado = false;
        System.out.println("────────  Mostrando Pedidos Por estado ─────── ");
        for (Pedido pedido : sistema.getListaPedidos().getListaMapGenerica().values()) {
            if (pedido.getEstado() == estadoPedido) {
                System.out.println("IDPedido: " + pedido.getIdPedido() +
                        ", Cliente: " + pedido.getIdCliente() +
                        ", Fecha: " + pedido.getFecha() +
                        ", Total: $" + pedido.getMontoTotal());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println(Etiquetas.INFO+" No hay pedidos con estado " + estadoPedido);
        }
    }


    /**
     * Lista todos los pedidos realizados por un cliente específico.
     *
     * @throws ElementoInexistenteEx Si el cliente no existe
     * @throws ListasVaciasEx        Si el cliente no realizó pedidos
     */
    public void listarPedidosPorCliente(SistemaTienda sistema, String idCliente) throws ElementoInexistenteEx , ListasVaciasEx{
        if(!sistema.getListaCliente().getListaMapGenerica().containsKey(idCliente)){
            throw new ElementoInexistenteEx("No existe esa id...");
        }
        if(sistema.getListaCliente().getPorId(idCliente).getHistorialPedidos().isEmpty()){
            throw new ListasVaciasEx(" el cliente no tiene pedidos...");
        }
        System.out.println("──────────  Mostrando Pedidos Del Cliente ────────── " );
        for (Pedido pedido : sistema.getListaPedidos().getListaMapGenerica().values()) {
            if (pedido.getIdCliente().equals(idCliente)) {
                System.out.println("IDPedido: " + pedido.getIdPedido() +
                        ", Fecha: " + pedido.getFecha() +
                        ", Total: $" + pedido.getMontoTotal() +
                        ", Estado: " + pedido.getEstado());
            }
        }
    }



}
