package Menu;

import Clases.Admin;
import Clases.Cliente;
import sistema.SistemaTienda;

public class Menu {

    /// Menu Clientes
    public static void menuCliente(SistemaTienda sistema, Cliente cliente){

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


    public static void menuAdmin(Admin admin, SistemaTienda sistema){













    }



}
