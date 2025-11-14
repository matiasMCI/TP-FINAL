package Clases;

import Enums.CategoriaProducto;
import Enums.ESTADO_CLIENTE;
import Enums.TIPO_CLIENTE;
import Excepciones.IDdontExistEX;
import Interfaces.IAdministrador;
import sistema.SistemaTienda;

import java.util.Map;
import java.util.Scanner;

public class Administrador extends Usuario implements IAdministrador {

    private String nombre;
    private int edad;
    private int idAdmin;

    ///-- CONSTRUCTOR --
    public Administrador( String nombre,String email, String contrasena, int edad, int idAdmin) {
        super(email, contrasena);
        this.nombre = nombre;
        this.edad = edad;
        this.idAdmin = idAdmin;
    }

    ///-- GETTERS SETTERS --

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    ///-- toSTRING --
    @Override
    public String toString() {
        return "Clases.Administrador{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", idAdmin=" + idAdmin +
                '}';
    }

    ///-- METODOS --


    public void DarDeBajaCliente(SistemaTienda sistema, int id){
        for(Cliente cliente : sistema.getListaDeClientes().listaGenerica){
            if(cliente.getIDusuario() == id){
                cliente.setEstado(ESTADO_CLIENTE.BAJA);
            }
        }
    }

    public void DarDeAltaCliente(SistemaTienda sistema,int id){
        for(Cliente cliente : sistema.getListaDeClientes().listaGenerica){
            if(cliente.getIDusuario() == id){
                cliente.setEstado(ESTADO_CLIENTE.ALTA);
            }
        }
    }

    public void ModificarCliente(SistemaTienda sistema, int id) throws IDdontExistEX{
        boolean flag = false;
        String continuar;
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese opcion: ");
        for(Cliente cliente : sistema.getListaDeClientes().listaGenerica){
            if(cliente.getIDusuario() == id){
                flag=true;
                do {
                    System.out.println("(1)Modificar nombre: ");
                    System.out.println("(2)Modificar email: ");
                    System.out.println("(3)Modificar contrasena: ");
                    System.out.println("(4)Modificar edad: ");
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    switch (opcion){
                        case 1:
                            String nombre = sc.nextLine();
                            cliente.setNombre(nombre);
                            break;
                        case 2:
                            String email = sc.nextLine();
                            cliente.setEmail(email);
                            break;
                        case 3:
                            String contrasena = sc.nextLine();
                            cliente.setContrasena(contrasena);
                            break;
                        case 4:
                            int edad = sc.nextInt();
                            cliente.setEdad(edad);
                            break;
                        default:
                            System.out.println("Opcion invalida");
                            break;

                }   System.out.println("Cambios aplicados");
                    System.out.println(cliente);

                    System.out.println(" Desea realizar otro cambio si/no:");

                    continuar = sc.nextLine();
                }  while (continuar.equalsIgnoreCase("si"));
            }
        }
        if (flag!=true) {
            throw new IDdontExistEX("No existe un cliente con esa ID en las lista...");
        }
    }



    public void ModificarProducto(SistemaTienda sistemaTienda, String id)throws IDdontExistEX {

        Producto producto = sistemaTienda.getListaDeProductos().getPorId(id);

        if(producto == null){
            throw new IDdontExistEX("No existe un producto con esa ID en la lista");

        }else {
            String continuar;
            Scanner sc = new Scanner(System.in);

            do {
                System.out.println("Ingrese opcion: ");
                System.out.println("-- Modificar Producto --");
                System.out.println("(1)Modificar Nombre");
                System.out.println("(2)Modificar precio");
                System.out.println("(3)Modificar Categoria");
                System.out.println("(4)Modificar descripcion");
                System.out.println("Elegir opcion: ");

                int opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese nuevo Nombre: ");
                        String nombre = sc.nextLine();
                        producto.setNombreProducto(nombre);
                        break;
                    case 2:
                        System.out.println("Ingrese nuevo precio:");
                        double precio = sc.nextDouble();
                        producto.setPrecio(precio);
                        break;
                    case 3:
                        System.out.println("Ingrese nueva categoria: ");
                        String categoriaString = sc.nextLine();
                        try{
                            CategoriaProducto categoriaProducto = CategoriaProducto.valueOf(categoriaString.toUpperCase());
                            producto.setCategoriaProducto(categoriaProducto);
                        }catch (IllegalArgumentException e){
                            System.out.println("Categoria invalida.");
                        }
                        break;
                    case 4:
                        System.out.println("Modificar descripcion: ");
                        String descripcion = sc.nextLine();
                        producto.setDescripcion(descripcion);
                        break;
                    default:
                        System.out.println("opcion invalida...");
                        break;
                }

                System.out.println("Cambios realizados: ");
                System.out.println(producto);

                System.out.println("Desea hacer otro cambio? si/no: ");
                continuar = sc.nextLine();
            }while(continuar.equalsIgnoreCase("si"));

        }
    }



    public void agregarCliente(SistemaTienda sistema, String email, String contrasena, String nombre, int edad, TIPO_CLIENTE tipoCliente, ESTADO_CLIENTE estado, double domicilio , double fondos){
        sistema.agregarCliente(email, contrasena, nombre, edad, tipoCliente, estado, domicilio, fondos);
    }

    public void DarDeBajaProducto(SistemaTienda sistema, String id){
        sistema.eliminarProducto(id);
    }
    public void DarDeAltaProducto(SistemaTienda sistema, String idProducto, String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion){

       sistema.agregarProducto(idProducto,nombreProducto,precio,categoriaProducto,descripcion);
    }
    public void MostrarMasVendidos(SistemaTienda sistema){




    }
    public void ClienteMasFrecuente(SistemaTienda sistema){

      int  i=0 , mayor=0;
      Cliente MayorCliente = null;
        for (Cliente c : sistema.getListaDeClientes().listaGenerica){
            for (HistorialDePedidos hp : c.getHistorialDeCompras()){
                i++;
            }
            if (i>mayor){
                mayor = i;
                MayorCliente = c;
            }
            i=0;
        }
        System.out.println(MayorCliente.getNombre()+MayorCliente.getDomicilio());
    }

    public void VerClientes(SistemaTienda sistema){
        sistema.VerListaDeTodosLosClientes();

    }
    public void VerListaDeProductos(SistemaTienda sistema){
        sistema.VerListaDeTodosLosProductos();
    }
    public void VerPedidos(SistemaTienda sistema){
        sistema.VerListaDeTodosLosPedidos();
    }


    public void cargarJsonProducto(SistemaTienda sistema ){
        sistema.subirJSONProducto();
    }

    public void cargarJsonClientes(SistemaTienda sistema){
        sistema.subirJSONClientes();
    }

}
