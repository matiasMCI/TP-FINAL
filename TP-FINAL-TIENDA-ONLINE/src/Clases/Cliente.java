package Clases;

import Enums.ESTADO_CLIENTE;
import Enums.TIPO_CLIENTE;
import Excepciones.IDdontExistEX;
import Excepciones.NameNotFoundEX;
import Excepciones.OpcionInvalidaEX;
import Interfaces.ICliente;
import sistema.SistemaTienda;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente extends Usuario implements ICliente {

/**
* La clase cliente
* representa al usuario que va a usar la pagina / aplicacion del mercado para su consumo
 * conntiene informacion como sus datos (nombre , edad , etc )
 * e implementa metodos creados en una intefar para poder usar las opciones
 * que la pagina permite a los clientes , como agregar al carrito , comprar , modificar su perfil , etc.
*
 */

    private String Nombre;
    private int Edad;
    private TIPO_CLIENTE TipoCliente;
    private ESTADO_CLIENTE estado;
    private List<Producto> carrito;
    private double domicilio ;
    private  List<HistorialDePedidos> historialDeCompras;
    private  double fondos ;
    private Object pedido1;

    ///-- CONSTRUCTOR --
    public Cliente( String email, String contrasena, String nombre, int edad, TIPO_CLIENTE tipoCliente, ESTADO_CLIENTE estado , double domicilio , double fondos ) {
        super(email, contrasena);
        this.Nombre = nombre;
        this.Edad = edad;
        this.TipoCliente = tipoCliente;
        this.estado = estado;
        this.domicilio = domicilio;
        this.fondos = fondos;

        carrito = new ArrayList<>();
        historialDeCompras = new ArrayList<>();
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

    public List<Producto> getProducto() {
        return carrito;
    }

    public void setProducto(List<Producto> carrito) {
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

    public List<HistorialDePedidos> getHistorialDeCompras() {
        return historialDeCompras;
    }

    public void setHistorialDeCompras(List<HistorialDePedidos> historialDeCompras) {
        this.historialDeCompras = historialDeCompras;
    }

    ///-- toSTRING --

    @Override
    public String toString() {
        return "Clases.Cliente{" +
                "Nombre='" + Nombre + '\'' +
                ", Edad='" + Edad + '\'' +
                ", TipoCliente=" + TipoCliente +
                ", estado=" + estado +
                ", carrito=" + carrito +
                '}';
    }
    ///-- METODOS --///


    ///  agrega un producto al carrito
    public void AgregarAlCarrito(Producto producto){
        carrito.add(producto);
    }

    ///  muestra el carrito
    public double mostrarCarrito(){
        double precioTotal = 0;
        for ( Producto c : carrito){
            precioTotal += c.getPrecio();
            System.out.println(c.toString());
        }



        System.out.println("precio Total: " + precioTotal);
        return precioTotal;
    }
///  eliminaa un elemento del carrito
    public void EliminarDelCarrito(String string) throws NameNotFoundEX {

int flag = 0;

    for(Producto c : carrito){
        if (c.getNombreProducto().equalsIgnoreCase(string)){
                    carrito.remove(c);
                    flag = 1;
        }
    }
        if(flag == 0){
             throw new NameNotFoundEX("El nombre ingresado no se encuentra en el carrito, nombre: " + string);
        }
    }
///  permite modificar al propio usuario sus datos
    public void ModificarPerfil(SistemaTienda sistema,int opcion , int id) throws OpcionInvalidaEX, IDdontExistEX {
        int flag = 0;
        String continuar = "no";
        Scanner sc = new Scanner(System.in);
        for (Cliente c : sistema.getListaDeClientes().listaGenerica){
            if (c.getIDusuario() == id){
                do {
                    flag = 1 ;

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
            System.out.println(iDdontExistEX.toString());
        }


    }
///  sirve para establecer el punto de entrega
    public void EstablecerDomicilioDeEntrega(SistemaTienda sistema,double nuevoDomicilio , int id)throws IDdontExistEX{
        int flag = 0;
        for (Cliente c : sistema.getListaDeClientes().listaGenerica){
            if(c.getIDusuario() == id){

                flag = 1;
                c.setDomicilio(nuevoDomicilio);
                System.out.println(c.getDomicilio());
            }
        }
        if(flag == 0){
            IDdontExistEX iDdontExistEX = new IDdontExistEX("El id no se encuentra registrado en el sistema");
            System.out.println(iDdontExistEX.toString());
        }

    }

    /// funcion para ir guardando los pedidos en un historial de pedidos
    public void AgregarHistorialDeCompra(Pedido pedido1){
        HistorialDePedidos d = new HistorialDePedidos(pedido1);
        historialDeCompras.add(d);
    }

/// realiza la compra del carrito entero
    public void RealizarCompra(SistemaTienda sistema,int id) throws IDdontExistEX{
    int flag = 0 ;
        for (Cliente c : sistema.getListaDeClientes().listaGenerica){
        if (c.getIDusuario() == id){
           flag = 1;
            double precioTotal = mostrarCarrito();
            c.setFondos(ConfirmarPago(sistema, precioTotal , c));
            System.out.println("Fondos: "+c.getFondos());
        }
    }
     if(flag == 0){
         IDdontExistEX iDdontExistEX = new IDdontExistEX("El id no esta registrado en el sistema");
         System.out.println(iDdontExistEX.toString());
     }
    }
/// funcion que se ejectua cada ves que se confirma una compra , crea un pedidio de ella
    public void crearPedido (SistemaTienda sistema , Cliente c){
        List<Producto> productarray = new ArrayList<>();

        for (Producto p : carrito){
            productarray.add(p);
        }

        Pedido pedido = new Pedido(c.getNombre(), productarray);
        sistema.agregarPedido(pedido);
        HistorialDePedidos hd = new HistorialDePedidos(pedido);
        historialDeCompras.add(hd);
    }
    ///  muestra el historial de pedidos
    public void verListaDePedidos(SistemaTienda sistema ) {

       for (HistorialDePedidos his : historialDeCompras)
        System.out.println(sistema.getListaDePedido().verGenerico());


    }
/// funcion que se ejecuta al usar la funcion de realizar compra , esta te pregunta si queres realizar el pago y crea el pedido con la funcionn crear pedido
    public double ConfirmarPago(SistemaTienda sistema ,double precioTotal , Cliente c){
        Scanner sc = new Scanner(System.in);
        String confirmacion = "no";
        double SF = 0;
        double saldoFinal;
        System.out.println("El precio total a pagar es de " + precioTotal);
        System.out.println("Desea Confirmar el pago ? ");
        confirmacion = sc.nextLine();
        if (confirmacion.equalsIgnoreCase("SI")){
            saldoFinal = c.getFondos()-precioTotal;
            crearPedido(sistema,c);
            SF=saldoFinal;
        }
        else {
            System.out.println("Pago cancelado");
        }
        return SF;
    }




}
