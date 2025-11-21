package Menu;

import Clases.Admin;
import Clases.Cliente;
import Excepciones.*;
import sistema.SistemaTienda;

import java.util.Scanner;

public class Menu {





    
    /// Menu Clientes
    public static void menuCliente(SistemaTienda sistema, Cliente cliente){
        Scanner sc = new Scanner(System.in);
        boolean logueado = true;

        while(logueado){
            System.out.println("─────────    Menu Cliente   ─────────");

            if(cliente.isEstado()){
                /// Si el cliente tiene la cuenta activa muestra este menu completo
                mostrarMenuActivo();
            }else{
                /// sino muestra este menu con menos opciones
                mostrarMenuInactivo();
            }
            System.out.println("Opcion: ");
            int opcion = sc.nextInt();
            sc.nextLine();
            logueado = procesarOpciones(sistema,cliente,opcion);
            sistema.subirJSON();

        }
    }



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

                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:

                    break;
                case 9:
                    break;
                case 10:
                    cliente.desactivar();
                    System.out.println("Cuenta desactivada");
                    break;
                case 11:
                    System.out.println( "Sesion cerrada");
                    return false;
                default:
                    System.out.println("Opcion invalida...");
                    break;
            }

        }else{

            /// CUENTA DESACTIVADA
            switch (opcion) {
                case 1:
                    cliente.mostrarPerfil();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Sesion cerrada!");
                    return false;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }

        }
        return true;
    }




    /// Menu inactivo, es decir si el cliente esta de baja
    public static void mostrarMenuInactivo(){
        System.out.println("1. Ver Perfil");
        System.out.println("2. Activar Cuenta");
        System.out.println("3. Modificar Perfil");
        System.out.println("4. Desloguearse");
        System.out.println("-------------------------------\n");
    }
    ///  Menu Activo, es decir si el cliente esta de alata
    public static void mostrarMenuActivo(){
        System.out.println("1. Ver Perfil");
        System.out.println("2. Agregar Fondos");
        System.out.println("3. Agregar productos al carrito");
        System.out.println("4. Mostrar Carrito");
        System.out.println("4. Eliminar productos del carrito");
        System.out.println("5. Realizar compra");
        System.out.println("6. Ver pedidos");
        System.out.println("7. Modificar perfil");
        System.out.println("8. Desactivar cuenta");
        System.out.println("9. Desloguearse");
        System.out.println("-------------------------------\n");
    }


    public static void menuAdmin(Admin admin, SistemaTienda sistema) {

        Scanner sc = new Scanner(System.in);
        boolean continuar = true;
        String idCliente;
        String idProducto;
        int cantidad;

        while (continuar) {
            System.out.println("────────────────────   ADMIN   ────────────────────");
            System.out.println("(1) Agregar Cliente" + "              \"(8) Mostrar Clientes\"");
            System.out.println("(2) Agregar Producto" + "             \"(9) Mostrar Productos\"");
            System.out.println("(3) Dar de alta Cliente" + "            \"(10) Agregar stock\"");
            System.out.println("(4) Dar de baja Cliente" + "            \"(11) Quitar stock\"");
            System.out.println("(5) Eliminar Producto" + "              \"(12) Mostrar Comprobantes\"");
            System.out.println("(6) Modificar Cliente" + "              \"(13) Mostrar Pedidos\"");
            System.out.println("(7) Modificar Producto" + "              \"(14) Cerrar sesion\"");
            System.out.println("────────────────────────────────────────────────────────");


            sistema.subirJSON();
            sistema.subirJSONProductos();

            System.out.println("Elegir: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    sistema.mostrarListaCliente();
                    break;
                case 9:
                    sistema.mostrarListaProducto();
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    continuar = false;
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("Opcion invalida...");
                    break;
            }

        }
    }





}
