package Clases;

import Excepciones.AccionImposibleEx;
import Excepciones.ElementoInexistenteEx;
import sistema.SistemaTienda;

import java.util.Scanner;

public class Admin extends Usuario {

    /**
     * Esta clase representa a los usuarios que van a poder controlar los clientes y productos
     * de la pagina / app de ventas del supermercado , es el rol que tiene acceso a metodos mas especificos
     * y importantes del sistema , como agregar productos o eliminar clientes
     */



    private String nombre;
    private int edad;
    private int idAdmin;

    /// CONSTRUCTOR NORMAL
    public Admin(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena);
        this.nombre = nombre;
        this.edad = edad;
        this.idAdmin = idAdmin;
    }
    /// CONSTRUCTO JSON
    public Admin(String IDUsuario, String nombre, String email, String contrasena){
        super(IDUsuario,nombre, email, contrasena);
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
        return "Admin{" + super.toString() + " }";
    }

    ///-- METODOS --

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
            System.out.println("2. Cambiar email");
            System.out.println("3. Cambiar contraseña");
            System.out.println("4. Cambiar edad ");
            System.out.println("5. Cambiar estado");
            System.out.println("6. Terminar");
            System.out.println("Elegir opcion: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1:
                    System.out.println("Ingrese nuevo nombre: ");
                    String nombre = sc.nextLine();
                    cliente.setNombre(nombre);
                    break;
                case 2:
                    System.out.println("Ingrese nuevo email: ");
                    String email = sc.nextLine();
                    cliente.setEmail(email);
                    break;
                case 3:
                    System.out.println("Ingrese nueva contraseña: ");
                    String contrasena = sc.nextLine();
                    cliente.setContrasena(contrasena);
                    break;
                case 4:
                    System.out.println("Ingrese nueva edad: ");
                    int edad = sc.nextInt();
                    try {
                        cliente.verificacionEdad(edad);
                    }catch (AccionImposibleEx e) {
                        System.out.println("error al asignar edad: " + e.getMessage());
                    }
                    break;
                case 5:

                    System.out.println("Estado: (1)Activar, (2) Desactivar: ");
                    int estadoEleccion = sc.nextInt();
                    try{
                        cliente.verificacionEstado(estadoEleccion);
                    }catch (AccionImposibleEx e){
                        System.out.println(" error al asignar estado: " + e.getMessage());
                    }
                    break;
                case 6:
                    confimar = false;
                    System.out.println("Cambio aplicados con exito!");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }

        }while(confimar);


    }



}
