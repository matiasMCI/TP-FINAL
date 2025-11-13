import Enums.ESTADO_CLIENTE;
import Enums.TIPO_CLIENTE;
import Excepciones.IDdontExistEX;
import Excepciones.NameNotFoundEX;
import Excepciones.OpcionInvalidaEX;
import Interfaces.ICliente;

import java.util.List;
import java.util.Scanner;

public class Cliente extends Usuario implements ICliente {



    private String Nombre;
    private int Edad;
    private TIPO_CLIENTE TipoCliente;
    private ESTADO_CLIENTE estado;
    private List<Carrito> carrito;
    private double domicilio ;
    private  List<HistorialDePedidos> HistorialDeCompras;
    private  double fondos ;
    ///-- CONSTRUCTOR --
    public Cliente( String email, String contrasena, String nombre, int edad, TIPO_CLIENTE tipoCliente, ESTADO_CLIENTE estado , double domicilio , double fondos ) {
        super(email, contrasena);
        this.Nombre = nombre;
        this.Edad = edad;
        this.TipoCliente = tipoCliente;
        this.estado = estado;
        this.domicilio = domicilio;
        this.fondos = fondos;
    }

    ///-- GETTERS SETTERS --
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public TIPO_CLIENTE getTipoCliente() {
        return TipoCliente;
    }

    public void setTipoCliente(TIPO_CLIENTE tipoCliente) {
        TipoCliente = tipoCliente;
    }

    public ESTADO_CLIENTE getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_CLIENTE estado) {
        this.estado = estado;
    }

    public List<Carrito> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<Carrito> carrito) {
        this.carrito = carrito;
    }

    public double getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(double domicilio) {
        this.domicilio = domicilio;
    }

    public double getFondos() {
        return fondos;
    }

    public void setFondos(double fondos) {
        this.fondos = fondos;
    }

    ///-- toSTRING --

    @Override
    public String toString() {
        return "Cliente{" +
                "Nombre='" + Nombre + '\'' +
                ", Edad='" + Edad + '\'' +
                ", TipoCliente=" + TipoCliente +
                ", estado=" + estado +
                ", carrito=" + carrito +
                '}';
    }
    ///-- METODOS --
    ///

    public void AgregarAlCarrito(Carrito producto){
        carrito.add(producto);
    }

    public double mostrarCarrito(){
        double precioTotal = 0;
        for (Carrito c : carrito){
            precioTotal += c.getProducto().getPrecio();
            System.out.println(c.toString());
        }
        System.out.println("precio Total: " + precioTotal);
        return precioTotal;
    }


    public void EliminarDelCarrito(String string) throws NameNotFoundEX {

int flag = 0;

    for(Carrito c : carrito){
        if (c.getProducto().getNombreProducto().equalsIgnoreCase(string)){
                    carrito.remove(c);
                    flag = 1;
        }
    }
        if(flag == 0){
             throw new NameNotFoundEX("El nombre ingresado no se encuentra en el carrito, nombre: " + string);
        }
    }

    public void ModificarPerfil(int opcion , int id) throws OpcionInvalidaEX, IDdontExistEX {
        int flag = 0;
        String continuar = "no";
        Scanner sc = new Scanner(System.in);
        for (Cliente c : SistemaTienda.getListaDeClientes().listaGenerica){
            if (c.getIDusuario() == id){
                do {
                    flag = 1 ;
                    System.out.println("(1)Modificar nombre: ");
                    System.out.println("(2)Modificar email: ");
                    System.out.println("(3)Modificar contrasena: ");
                    System.out.println("(4)Modificar edad: ");
                    switch (opcion){
                        case 1 :
                            System.out.println("ingrese el nuevo nombre");
                            String nombre = sc.nextLine();
                            c.setNombre(nombre);
                            break;
                        case 2:
                            System.out.println("ingrese su edad correcta");
                            int edadNueva = sc.nextInt();
                            c.setEdad(edadNueva);
                            break;
                        case 3:
                            System.out.println("ingrese su nuevo Email");
                            String nuevoEmail = sc.nextLine();
                            c.setEmail(nuevoEmail);
                            break;
                        case 4:
                            System.out.println("ingrese su nueva contraseña");
                            String nuevaContraseña = sc.nextLine();
                            c.setContrasena(nuevaContraseña);
                            break;

                        default:

                            OpcionInvalidaEX ex = new OpcionInvalidaEX("La opcione es invalida");
                            break;

                    }
                    System.out.println("Cambios aplicados");
                    System.out.println(c);
                    System.out.println(" Desea realizar otro cambio si/no");
                    continuar = sc.nextLine();

                } while (continuar.equalsIgnoreCase("si"));

            }
        }
        if (flag == 0){
            IDdontExistEX iDdontExistEX = new IDdontExistEX("El id ingresado no se encuentra en sistema");
        }


    }

    public void EstablecerDomicilioDeEntrega(double nuevoDomicilio , int id)throws IDdontExistEX{
        int flag = 0;
        for (Cliente c : SistemaTienda.getListaDeClientes().listaGenerica){
            if(c.getIDusuario() == id){

                flag = 1;
                c.setDomicilio(nuevoDomicilio);

            }
        }
        if(flag == 0){
            IDdontExistEX iDdontExistEX = new IDdontExistEX("El id no se encuentra registrado en el sistema");
        }

    }

    public void HistorialDeCompra(){

    }


    public void RealizarCompra(int id) throws IDdontExistEX{
    int flag = 0 ;
        for (Cliente c : SistemaTienda.getListaDeClientes().listaGenerica){
        if (c.getIDusuario() == id){
           flag = 1;
            double precioTotal = mostrarCarrito();
            c.setFondos(ConfirmarPago(precioTotal , c));
        }
    }
     if(flag == 0){
         IDdontExistEX iDdontExistEX = new IDdontExistEX("El id no esta registrado en el sistema");
     }
    }

    public Pedido crearPedido (Cliente c){




    return Pedido;
    }



    public double ConfirmarPago(double precioTotal , Cliente c){
        Scanner sc = new Scanner(System.in);
        String confirmacion = "no";
        double saldoFinal;
        System.out.println("El precio total a pagar es de " + precioTotal);
        System.out.println("Desea Confirmar el pago ? ");
        confirmacion = sc.nextLine();
        if (confirmacion.equalsIgnoreCase("SI")){
            saldoFinal = c.getFondos()-precioTotal;
            crearPedido(c);
            return saldoFinal;
        }
        else {
            System.out.println("Pago cancelado");
        }
    }

    public void EstadoPedido(){

    }
    public void DevolverProducto(){

    }


}
