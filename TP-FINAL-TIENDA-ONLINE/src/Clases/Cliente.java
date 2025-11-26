package Clases;

import Excepciones.*;
import Interfaces.ICliente;
import Utilidades.Etiquetas;
import sistema.SistemaTienda;

import java.util.*;

/**
 * Clase Cliente.
 *
 * Representa un usuario cliente de la tienda online.
 * Contiene información personal (nombre, email, edad), fondos disponibles,
 * estado de la cuenta, carrito de compras actual y un historial de pedidos realizados.
 * Hereda de la clase Usuario.
 */

public class Cliente extends Usuario implements ICliente {




    private int edad;
    private  double fondos ;
    private boolean estado;
    private Map<String, ItemCarrito> carrito = new HashMap<>();
    private List<Pedido> historialPedidos = new ArrayList<>();


    /**
     * Constructor principal.
     * Crea un cliente nuevo con edad, nombre, email y contraseña.
     * Inicializa fondos en 0 y estado en false.
     */
    public Cliente(String nombre, String apellido,String email, String contrasena, int edad){
        super(nombre,apellido,email,contrasena);
        this.edad = edad;
        this.fondos = 0;
        this.estado = false;
    }
    /**
     * Constructor para deserialización desde JSON.
     *
     * @param IDUsuario ID único del usuario.
     * @param nombre Nombre del cliente.
     * @param email Email del cliente.
     * @param contrasena Contraseña del cliente.
     * @param edad Edad del cliente.
     * @param fondos Fondos disponibles.
     * @param estado Estado de la cuenta.
     */
    public Cliente(String IDUsuario, String nombre,String apellido, String email, String contrasena, int edad, double fondos, boolean estado){
        super(IDUsuario,nombre,apellido,email,contrasena);
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

    /**
     * Devuelve la lista de pedidos realizados por el cliente.
     *
     * @return Lista de objetos Pedido.
     */
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
        System.out.println("Apellido : " + this.getApellido());
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
        System.out.println(Etiquetas.EXITO +"fondos cargados!");
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
        System.out.println(Etiquetas.EXITO+"Producto eliminado con exito!");
    }
    /// Esta Funcion vacia el carrito por completo,
    /// Si el carrito ya esta vacio arroja CarritoVacioEx
    public void vaciarCarrito()throws CarritoVacioEx {
        if(carrito.isEmpty()){
            throw new CarritoVacioEx("El carrito ya esta vacio");
        }else {
            carrito.clear();
            System.out.println(Etiquetas.EXITO+"Carrito vaciado con exito!");
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
            System.out.println(Etiquetas.EXITO+"Agregado exitosamente!\n\n");

        } else {
            carrito.put(producto.getIdProducto(), new ItemCarrito(producto,cantidad));
            System.out.println(Etiquetas.EXITO+"Agregado exitosamente!\n\n");
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
        System.out.println(Etiquetas.INFO+"Compra finalizada con exito!\n");
        sistema.agregarPedido(pedido);
        carrito.clear();
        sistema.subirJSONPedidos();
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
                        item.getCantidad() + "----- $" + subtotal);
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
        System.out.println(Etiquetas.INFO+"estado cambiado");
    }
    /// Verificara que la edad pasada por parametro este entre 18 y 99
    public void verificacionEdad(int edad)throws AccionImposibleEx{
        if(edad < 18 || edad > 99){
            throw new AccionImposibleEx("la edad debe estar entre 18 y 99(Años)...");
        }
        this.setEdad(edad);
        System.out.println(Etiquetas.INFO+"edad cambiada");
    }


    /// Metodo para modificarPerfil
    public void modificarPerfil(SistemaTienda sistema,Cliente cliente){
        Scanner sc = new Scanner(System.in);
        boolean confimar = true;
        System.out.println("───────────  Modificacion de perfil  ───────────");

        do{

            System.out.println("\n1. Cambiar nombre");
            System.out.println("2. Cambiar Apellido");
            System.out.println("3. Cambiar email");
            System.out.println("4. Cambiar contraseña");
            System.out.println("5. Cambiar edad ");
            System.out.println("6. Terminar");

            int opcion = sistema.leerEnteroSeguro(sc,"Opcion: ");

            switch (opcion){
                case 1:
                    System.out.println("Ingrese nuevo nombre: ");
                    String nombre = sc.nextLine();
                    cliente.setNombre(nombre);
                    System.out.println(Etiquetas.INFO+"nombre cambiado");
                    break;
                case 2:
                    System.out.println("Ingrese nuevo apellido: ");
                    String apellido = sc.nextLine();
                    cliente.setApellido(apellido);
                    System.out.println(Etiquetas.INFO+"apellido cambiado");
                case 3:
                    System.out.println("Ingrese nuevo email: ");
                    String email = sc.nextLine();
                    cliente.setEmail(email);
                    System.out.println(Etiquetas.INFO+"email cambiado");
                    break;
                case 4:
                    System.out.println("Ingrese nueva contraseña: ");
                    String contrasena = sc.nextLine();
                    cliente.setContrasena(contrasena);
                    System.out.println(Etiquetas.INFO+"contraseña cambiada");
                    break;
                case 5:
                    int edad = sistema.leerEnteroSeguro(sc, "Ingrese nueva edad: ");
                    try {
                        cliente.verificacionEdad(edad);
                    }catch (AccionImposibleEx e) {
                        System.out.println(Etiquetas.ERROR + "al asignar edad: " + e.getMessage());
                    }
                    break;
                case 6:
                    confimar = false;
                    System.out.println(Etiquetas.EXITO+"Cambio aplicados con exito!");
                    break;
                default:
                    System.out.println(Etiquetas.WARNING+"Opcion invalida");
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





}
