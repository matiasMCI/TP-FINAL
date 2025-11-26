package Clases;

import Enums.CategoriaProducto;
import Excepciones.AccionImposibleEx;
import Excepciones.ElementoInexistenteEx;
import Excepciones.ListasVaciasEx;
import Utilidades.Etiquetas;
import sistema.SistemaTienda;

import java.util.Scanner;

public class Admin extends Usuario {

    /**
     * Esta clase representa a los usuarios que van a poder controlar los clientes y productos
     * de la pagina / app de ventas del supermercado , es el rol que tiene acceso a metodos mas especificos
     * y importantes del sistema , como agregar productos o eliminar clientes
     */

    /// CONSTRUCTOR NORMAL
    public Admin(String nombre, String apellido,String email, String contrasena) {
        super(nombre, apellido,email, contrasena);

    }
    /// CONSTRUCTO JSON
    public Admin(String IDUsuario, String nombre,String apellido, String email, String contrasena){
        super(IDUsuario,nombre,apellido, email, contrasena);
    }


    ///-- toSTRING --
    @Override
    public String toString() {
        return "Admin{" + super.toString() + " }";
    }

    ///-- METODOS --


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

    /// Metodos para ALTA / BAJA CLIENTE MEDIANTE IDUsuario
    public void darDeBajaCliente(SistemaTienda sistema,String id)throws ElementoInexistenteEx{
        cambiarEstadoCliente(sistema,id,false);
    }
    public void darDeAltaCliente(SistemaTienda sistema,String id)throws  ElementoInexistenteEx{
        cambiarEstadoCliente(sistema, id,true);
    }
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

    /// METODO PARA AGREGAR UN PRODUCTO
    ///
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


    /// METODO PARA MODIFICAR DATOS DE UN PRODUCTO
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


    /// AGREGAR O QUITAR STOCK
    public void agregarStock(SistemaTienda sistema,String  id, int cantidad)throws ElementoInexistenteEx{
        Producto producto = sistema.getListaProductos().getPorId(id);
        if(producto == null){
            throw new ElementoInexistenteEx("El producto no existe...");
        }
        producto.setStock(producto.getStock() + cantidad);
        System.out.println(Etiquetas.EXITO +"Stock agregado con exito!");
    }
    public void quitarStock(SistemaTienda sistema,String id, int cantidad)throws ElementoInexistenteEx{
        Producto producto = sistema.getListaProductos().getPorId(id);
        if(producto == null){
            throw new ElementoInexistenteEx("El producto no existe...");
        }
        /// Si la resta da menor a 0, seteamos en 0 el stock. para que el stock no sea negativo
        if((producto.getStock() - cantidad) < 0){
            producto.setStock(0);
        }
        producto.setStock(producto.getStock() - cantidad);
        System.out.println(Etiquetas.EXITO +"Stock descontado con exito!");
    }



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

    /// Metodo para buscar al cliente mas frecuente

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














}
