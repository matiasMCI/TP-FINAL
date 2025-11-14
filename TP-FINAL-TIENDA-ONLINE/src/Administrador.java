import Enums.CategoriaProducto;
import Enums.ESTADO_CLIENTE;
import Interfaces.IAdministrador;

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
        return "Administrador{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", idAdmin=" + idAdmin +
                '}';
    }

    ///-- METODOS --


    public void DarDeBajaCliente(int id){
        for(Cliente cliente : SistemaTienda.getListaDeClientes().listaGenerica){
            if(cliente.getIDusuario() == id){
                cliente.setEstado(ESTADO_CLIENTE.BAJA);
            }
        }
    }

    public void DarDeAltaCliente(int id){
        for(Cliente cliente : SistemaTienda.getListaDeClientes().listaGenerica){
            if(cliente.getIDusuario() == id){
                cliente.setEstado(ESTADO_CLIENTE.ALTA);
            }
        }
    }

    public void ModificarCliente(int id){
        String continuar = "no";
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese opcion: ");
        for(Cliente cliente : SistemaTienda.getListaDeClientes().listaGenerica){
            if(cliente.getIDusuario() == id){
                do {
                    System.out.println("(1)Modificar nombre: ");
                    System.out.println("(2)Modificar email: ");
                    System.out.println("(3)Modificar contrasena: ");
                    System.out.println("(4)Modificar edad: ");
                    int opcion = sc.nextInt();
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
    }

    public void ModificarProducto(){

    }
    public void DarDeBajaProducto(String id){
        SistemaTienda.eliminarProducto(id);
    }
    public void DarDeAltaProducto(String idProducto, String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion){
        SistemaTienda.agregarProducto(idProducto,nombreProducto,precio,categoriaProducto,descripcion);
    }
    public void mostrarListaProductos(){
        SistemaTienda.VerListaDeTodosLosProductos();
    }
    public void MostrarMasVendidos(){

    }
    public void ClienteMasFrecuente(){

    }
    public void VerClientes(){
        SistemaTienda.VerListaDeTodosLosClientes();
    }
    public void VerListaDeProductos(){
        SistemaTienda.VerListaDeTodosLosProductos();
    }
    public void VerPedidos(){

    }
    public void VerComprobantes(){

    }

}
