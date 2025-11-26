package Clases;

import Excepciones.*;
import sistema.SistemaTienda;

import java.util.*;

public class Cliente extends Usuario  {




    private int edad;
    private  double fondos ;
    private boolean estado;
    private Map<String, ItemCarrito> carrito = new HashMap<>();
    private List<Pedido> historialPedidos = new ArrayList<>();


    ///-- CONSTRUCTOR NORMAL
    public Cliente(String nombre, String email, String contrasena, int edad){
        super(nombre,email,contrasena);
        this.edad = edad;
        this.fondos = 0;
        this.estado = false;
    }
    /// CONSTRUCTOR PARA JSON
    public Cliente(String IDUsuario, String nombre, String email, String contrasena, int edad, double fondos, boolean estado){
        super(IDUsuario,nombre,email,contrasena);
        this.edad = edad;
        this.fondos = fondos;
        this.estado = estado;
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

    public List<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    public void setHistorialPedidos(List<Pedido> historialPedidos) {
        this.historialPedidos = historialPedidos;
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
        System.out.println("ID: " + this.getIdUsuario());
        System.out.println("Nombre: " + this.getNombre());
        System.out.println("Edad: " + edad);
        System.out.println("Email: " + this.getEmail());
        System.out.println("Contraseña: " + this.getContrasena());
        System.out.println("Fondos: " + fondos);
        System.out.println("Estado: " + conversorEstado());
        System.out.println("─────────────────────────────────\n");
    }

    /// Funcion para agregar Fondos al cliente
    /// Esta consta de una limite de 500,000 pesos.
    /// No se pueden agregar fondos negativos
    ///
    public void agregarFondos(double fondos)throws AccionImposibleEx,FondosSuperadosEx{
        if(fondos <= 0){
            throw new AccionImposibleEx("No se puede ingresar negativos...");
        }
        if((this.fondos  + fondos)> 500000){
            throw new FondosSuperadosEx("Excederas los fondos maximos($500,000)");
        }
        this.setFondos(this.fondos + fondos);
        System.out.println("fondos cargados!");
        System.out.println("Total Fondos: " + this.fondos);
    }

    /// Funcion para eliminar un producto que este en el carrito mediante su ID
    /// Si no esta arroja un excepcion -> ElementoInexistenteEx
    public void eliminarDeCarrito(String IDProducto)throws ElementoInexistenteEx {
        /// Comprueba si existe ese producto en el carrito por la IDProducto
        if(!carrito.containsKey(IDProducto)){
            throw new ElementoInexistenteEx("No existe ese producto en el carrito...");
        }
        carrito.remove(IDProducto);
        System.out.println("Producto eliminado con exito!");
    }
    /// Esta Funcion vacia el carrito por completo,
    /// Si el carrito ya esta vacio arroja CarritoVacioEx
    public void vaciarCarrito()throws CarritoVacioEx {
        if(carrito.isEmpty()){
            throw new CarritoVacioEx("El carrito ya esta vacio");
        }else {
            carrito.clear();
            System.out.println("Carrito vaciado con exito!");
        }
    }

    /// Esta funcion agrega al carrito un producto por su ID y permite setear una cantidad.
    /// Si en las listaProductos no existe la IDProducto pasada por parametro arroja ElementoInexistenteEx
    /// Si ya no hay stock del producto arroja SinStockEx
    ///
    public void agregarACarrito(SistemaTienda sistema, String IDProducto, int cantidad) throws SinStockEx, ElementoInexistenteEx {

        /// Compureba si existe la idProducto
        if(!sistema.getListaProductos().existeId(IDProducto)){
            throw new ElementoInexistenteEx("No existe un producto con esa id...");
        }
        /// Si existe lo agarra yguarda en un objeto Producto
        Producto producto = sistema.getListaProductos().getPorId(IDProducto);
        /// Comprueba si hay suficiente stock
        if(sistema.getListaProductos().getPorId(producto.getIdProducto()).getStock() < cantidad){
            throw new SinStockEx("No hay suficiente stock...");
        }
        /// Comprueba si ese producto ya esta en el carrito
        /// Si lo esta suma la cantidad dada a la que ya esta en carrito
        if(carrito.containsKey(producto.getIdProducto())){
            ItemCarrito itemCarrito = carrito.get(producto.getIdProducto());
            /// Comprueba si sigue habiendo suficiente stock
            if(sistema.getListaProductos().getPorId(producto.getIdProducto()).getStock() < itemCarrito.getCantidad() + cantidad){
                throw new SinStockEx("No hay suficiente stock para aumentar la cantidad...");
            }
            itemCarrito.setCantidad(itemCarrito.getCantidad() + cantidad);
            System.out.println("Agregado exitosamente!\n\n");

        } else {
            carrito.put(producto.getIdProducto(), new ItemCarrito(producto,cantidad));
            System.out.println("Agregado exitosamente!\n\n");
        }
    }

    /// Esta funcion calcula el total del carrito y lo devuelve
    public double totalCarrito(){
        double total = 0;
        for(ItemCarrito item : carrito.values()){
            total += item.getCantidad() * item.getProducto().getPrecio();
        }
        return total;
    }


    /// Este metodo va a crear un nuevo pedido con los Items que esten en el carrito
    /// Verifica que el carrito no este vacio, sino arroja CarritoVacioEx
    /// Verifica que los fondos sean suficientes sino arroja FondosInsuficienteEx
    /// Por ultimo vacia el carrito
    public void finalizarCompra(SistemaTienda sistema)throws CarritoVacioEx, FondosInsuficientesEx {
        if(carrito.isEmpty()){
            throw new CarritoVacioEx( "El carrito esta vacio...");
        }
        if(this.fondos < totalCarrito()) {
            throw new FondosInsuficientesEx("Fondos insuficientes. Te faltan: " + (totalCarrito() - this.fondos) +" pesos...");
        }
        /// Busco en la lista Productos de mi sistema los productos que tenga la misma id que en mi carrito
        ///  y les descuento el la cantidad de stock
        for(Producto producto : sistema.getListaProductos().getListaMapGenerica().values()){
            if(carrito.containsKey(producto.getIdProducto())){
                producto.setStock(producto.getStock() - carrito.get(producto.getIdProducto()).getCantidad());
            }
        }
        setFondos(getFondos() - totalCarrito());

        Pedido pedido = new Pedido(getIdUsuario(),carrito);
        historialPedidos.add(pedido);
        System.out.println("Compra finalizada con exito!\n");
        sistema.agregarPedido(pedido);
        carrito.clear();
    }

    /// Este metodo te permite ver todos los pedidos hechos por el Cliente
    public void verPedidos()throws ListasVaciasEx {
        if(historialPedidos.isEmpty()){
            throw  new ListasVaciasEx("Sin historial de pedidos...");
        }
        System.out.println("───────────  HISTORIAL DE MIS PEDIDOS ───────────");
        for(Pedido pedido : historialPedidos){
            System.out.println("IDPedido: " + pedido.getIdPedido());
            System.out.println("Fecha: " + pedido.getFecha());
            for(ItemCarrito item : pedido.getItems().values()){
                double subtotal = item.getCantidad() * item.getProducto().getPrecio();
                System.out.println("- " + item.getProducto().getNombreProducto() + " x" +
                        item.getCantidad() + "----- $" + subtotal + "\n");
            }
            System.out.println("Total: " + pedido.getMontoTotal());
            System.out.println("Estado: " + pedido.getEstado());
            System.out.println("─────────────────────────────────\n");
        }
    }
 /// Este metodo te permite ver tu carrito
    public void mostrarCarrito()throws ListasVaciasEx{
        if(carrito.isEmpty()){
            throw new ListasVaciasEx("El carrito esta vacio...");
        }
        System.out.println("─────────── Carrito de Compras ───────────");
        double total = 0;
        for(ItemCarrito item : carrito.values()){
            Producto p = item.getProducto();
            int cant = item.getCantidad();
            double subtotal = p.getPrecio() * cant;
            total += subtotal;
            System.out.println("ID: " + p.getIdProducto());
            System.out.println("Nombre: " + p.getNombreProducto());
            System.out.println("Cantidad: " + cant);
            System.out.println("Precion unitario: " + p.getPrecio());
            System.out.println("Subtotal: $" + subtotal);
            System.out.println("─────────────────────────────────");
        }
        System.out.println("Total a Pagar: $" + total);
        System.out.println("\n\n");
    }
    /// Verificara que la eleccion sea 1 o 2
    public void verificacionEstado(int eleccion)throws AccionImposibleEx{
        if(eleccion < 1 || eleccion > 2){
            throw  new AccionImposibleEx("elegir entre 1 o 2...");
        }
        if(eleccion == 1){
            this.activar();
        }else if(eleccion == 2){
            this.desactivar();
        }
        System.out.println("estado cambiado");
    }
    /// Verificara que la edad pasada por parametro este entre 18 y 99
    public void verificacionEdad(int edad)throws AccionImposibleEx{
        if(edad < 18 || edad > 99){
            throw new AccionImposibleEx("la edad debe estar entre 18 y 99(Años)...");
        }
        this.setEdad(edad);
        System.out.println("edad cambiada");
    }


    /// Metodo para modificarPerfil
    public void modificarPerfil(SistemaTienda sistema,Cliente cliente){
        Scanner sc = new Scanner(System.in);
        boolean confimar = true;
        System.out.println("───────────  Modificacion de perfil  ───────────");

        do{

            System.out.println("\n1. Cambiar nombre");
            System.out.println("2. Cambiar email");
            System.out.println("3. Cambiar contraseña");
            System.out.println("4. Cambiar edad ");
            System.out.println("5. Terminar");

            int opcion = sistema.leerEnteroSeguro(sc,"Opcion: ");

            switch (opcion){
                case 1:
                    System.out.println("Ingrese nuevo nombre: ");
                    String nombre = sc.nextLine();
                    cliente.setNombre(nombre);
                    System.out.println("nombre cambiado");
                    break;
                case 2:
                    System.out.println("Ingrese nuevo email: ");
                    String email = sc.nextLine();
                    cliente.setEmail(email);
                    System.out.println("email cambiado");
                    break;
                case 3:
                    System.out.println("Ingrese nueva contraseña: ");
                    String contrasena = sc.nextLine();
                    cliente.setContrasena(contrasena);
                    System.out.println("contraseña cambiada");
                    break;
                case 4:
                    int edad = sistema.leerEnteroSeguro(sc, "Ingrese nueva edad: ");
                    try {
                        cliente.verificacionEdad(edad);
                    }catch (AccionImposibleEx e) {
                        System.out.println("Error al asignar edad: " + e.getMessage());
                    }
                    break;
                case 5:
                    confimar = false;
                    System.out.println("Cambio aplicados con exito!");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }while(confimar);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                super.toString() +
                "edad=" + edad +
                ", fondos=" + fondos +
                ", estado=" + estado +
                '}';
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
