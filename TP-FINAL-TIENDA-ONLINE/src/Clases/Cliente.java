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
import java.util.Map;
import java.util.Scanner;

public class Cliente extends Usuario  {




    private int edad;
    private  double fondos ;
    private boolean estado;
    private Map<String, ItemCarrito> carrito;
    private List<Pedido> historialDeCompras;


    ///-- CONSTRUCTOR --
    public Cliente(String nombre, String email, String contrasena, int edad){
        super(nombre,email,contrasena);
        this.edad = edad;
        this.fondos = 0;
        this.estado = false;
    }

    ///-- GETTERS SETTERS --
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getFondos() {
        return fondos;
    }

    public void setFondos(double fondos) {
        this.fondos = fondos;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Map<String, ItemCarrito> getCarrito() {
        return carrito;
    }

    public void setCarrito(Map<String, ItemCarrito> carrito) {
        this.carrito = carrito;
    }

    public List<Pedido> getHistorialDeCompras() {
        return historialDeCompras;
    }

    public void setHistorialDeCompras(List<Pedido> historialDeCompras) {
        this.historialDeCompras = historialDeCompras;
    }
    public boolean isEstado() {
        return estado;
    }

    public void activar(){
        estado = true;
    }
    public void desactivar(){
        estado = false;
    }

    public String conversorEstado(){
        if(isEstado()){
            return "Cuenta activada";
        }else{
            return "Cuenta desactivada";
        }
    }

    ///-- METODOS --///
    public void mostrarPerfil(){
        System.out.println("\n─────────── Perfil Cliente ───────────");
        System.out.println("ID: " + this.getIDusuario());
        System.out.println("Nombre: " + this.getNombre());
        System.out.println("Edad: " + edad);
        System.out.println("Email: " + this.getEmail());
        System.out.println("Contraseña: " + this.getContrasena());
        System.out.println("Fondos: " + fondos);
        System.out.println("Estado: " + conversorEstado());
        System.out.println("─────────────────────────────────\n");
    }








///  permite modificar al propio usuario sus datos

///  sirve para establecer el punto de entrega
   /* public void EstablecerDomicilioDeEntrega(SistemaTienda sistema,String nuevoDomicilio , String id)throws IDdontExistEX{
        int flag = 0;
        for (Cliente c : sistema.getListaDeClientes().listaGenerica){
            if(c.getIDusuario().equals(id)){

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
    }*/
/*
/// realiza la compra del carrito entero

    public void RealizarCompra(SistemaTienda sistema,String id) throws IDdontExistEX{
    int flag = 0 ;
        for (Cliente c : sistema.getListaDeClientes().listaGenerica){
        if (c.getIDusuario().equals(id)){
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
*/



}
