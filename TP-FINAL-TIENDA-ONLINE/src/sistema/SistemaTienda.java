package sistema;

import Clases.*;
import Enums.CategoriaProducto;
import Enums.EstadoPedido;
import Excepciones.ElementoDuplicadoEx;
import Excepciones.ElementoInexistenteEx;
import Excepciones.ListasVaciasEx;
import JSONUtiles.JSONUtiles;
import Menu.Menu;
import Utilidades.Etiquetas;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Clase SistemaTienda.
 * Gestiona los productos, clientes, pedidos y administradores de la tienda.
 * Permite agregar, eliminar y consultar elementos, y exportar datos a JSON.
 *
 */

public class SistemaTienda {

    private static final String archivoCompleto = "tiendaOnlineDatos";
    private static final String  archivoProductos = "tiendaOnlineProductos";
    private static final String archivoPedidos = "tiendaOnlinePedidos";



    private ClaseGenericaMap<Admin> listaAdmin;
    private ClaseGenericaMap<Cliente> listaCliente;
    private ClaseGenericaMap<Producto> listaProductos;
    private ClaseGenericaMap<Pedido>listaPedidos;



    /**
     * Constructor. Inicializa las listas de productos, clientes, pedidos y administradores.
     */

    public SistemaTienda() {
        listaAdmin = new ClaseGenericaMap<>();
        listaCliente = new ClaseGenericaMap<>();
        listaProductos = new ClaseGenericaMap<>();
        listaPedidos = new ClaseGenericaMap<>();
    }
    /// Json a las listas
    public SistemaTienda(JSONObject data, JSONArray data2, JSONArray dataPedido) throws JSONException {
        this();

        /// Variable para guardar al maximo ID
        int maxID = 0;
        String id;

        /// Llenado de lista Admins
        JSONArray jsonArrayAdmins = data.getJSONArray("admins");
        for(int i = 0; i < jsonArrayAdmins.length(); i++){
            JSONObject jsonObjectAdmin = jsonArrayAdmins.getJSONObject(i);
            id = jsonObjectAdmin.getString("IDUsuario");
            int num = Integer.parseInt(id.replace("ID",""));
            if(num > maxID){
                maxID = num;
            }
            JSONAgregarAdmin(jsonObjectAdmin.getString("IDUsuario"),jsonObjectAdmin.getString("nombre"),
                    jsonObjectAdmin.getString("email"),jsonObjectAdmin.getString("contraseña"));
        }

        /// Llenado de listas Clientes
        JSONArray jsonArrayClientes = data.getJSONArray("clientes");
        for(int i = 0; i < jsonArrayClientes.length();i++) {
            JSONObject jsonObjectCliente = jsonArrayClientes.getJSONObject(i);

            /// Va agarrando ID y buscando el maximo
            id = jsonObjectCliente.getString("IDUsuario");
            int num = Integer.parseInt(id.replace("ID", ""));
            if (num > maxID) {
                maxID = num;
            }
            JSONAgregarCliente(jsonObjectCliente.getString("IDUsuario"),
                    jsonObjectCliente.getString("nombre"), jsonObjectCliente.getString("email"),
                    jsonObjectCliente.getString("contraseña"), jsonObjectCliente.getInt("edad"),
                    jsonObjectCliente.getDouble("fondos"), jsonObjectCliente.getBoolean("estado")
            );
        }
        /// Encuentro el maximo ID y seteo mi contador en el siguiente a ese
        Usuario.setContador(maxID+1);

        /// /////////////////////
        int maxIDProducto = 0;
        String idProducto;
        /// Lista productos
        for(int i = 0; i < data2.length(); i++){
            JSONObject jsonObjectProducto = data2.getJSONObject(i);

            idProducto = jsonObjectProducto.getString("idProducto");
            int num = Integer.parseInt(idProducto.replace("ID",""));
            if(num > maxIDProducto){
                maxIDProducto = num;
            }
            JSONAgregarProducto(jsonObjectProducto.getString("idProducto"),
                    jsonObjectProducto.getString("nombreProducto"),
                    jsonObjectProducto.getDouble("precio"),
                    jsonObjectProducto.getEnum(CategoriaProducto.class,"categoriaProducto"),
                    jsonObjectProducto.getString("descripcion"),
                    jsonObjectProducto.getInt("stock"));
        }
        Producto.setContador(maxIDProducto+1);

///  ////////////////////////


        int maxIDPedido = 0;
        String idPedido;

        for (int i = 0; i < dataPedido.length(); i++) {

            JSONObject jsonPedido = dataPedido.getJSONObject(i);

            // ------------ ID ------------
            idPedido = jsonPedido.getString("idPedido");
            int num = Integer.parseInt(idPedido.replace("PED", ""));
            if (num > maxIDPedido) {
                maxIDPedido = num;
            }

            Pedido pedido = new Pedido( jsonPedido.getString("idPedido"),
                    jsonPedido.getDouble("montoTotal"),
                    jsonPedido.getString("idCliente"),
                    jsonPedido.getString("fecha"),
                    jsonPedido.getEnum(EstadoPedido.class, "estado")
            );

            ///  Items
            JSONArray arrayItems = jsonPedido.getJSONArray("items");
            Map<String, ItemCarrito> mapaItems = new HashMap<>();

            for (int j = 0; j < arrayItems.length(); j++) {

                JSONObject jsonItem = arrayItems.getJSONObject(j);

                idProducto = jsonItem.getString("idProducto");
                int cantidad = jsonItem.getInt("cantidad");

                ///  Producto
                Producto prod = listaProductos.getPorId(idProducto);

                ItemCarrito item = new ItemCarrito(prod, cantidad);

                mapaItems.put(idProducto, item);
            }

            pedido.setItems(mapaItems);
            listaPedidos.agregarGenerico(pedido.getIdPedido(), pedido);

            /// Lleno las lista historialPedidos de cada uno de los clientes
            Cliente cliente = listaCliente.getPorId(pedido.getIdCliente());
            if (cliente != null) {
                cliente.getHistorialPedidos().add(pedido);
            }

        }
        /// Setear contador
        Pedido.setContador(maxIDPedido + 1);









    }


    ///-- METODOS --


    /// Funciones de agregarcion por menu
    public void agregarCliente(String nombre, String email, String contrasena, int edad){
        Cliente cliente = new Cliente(nombre, email, contrasena, edad);
        listaCliente.agregarGenerico(cliente.getIdUsuario(),cliente);
    }
    public void agregarAdmin(String nombre, String email, String contrasena){
        Admin admin = new Admin(nombre, email, contrasena);
        listaAdmin.agregarGenerico(admin.getIdUsuario(),admin);
    }
    public void agregarProducto(String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion, int stock)throws  ElementoDuplicadoEx{
        Producto producto = new Producto(nombreProducto, precio, categoriaProducto, descripcion, stock);
        listaProductos.agregarGenerico(producto.getIdProducto(),producto);
    }
    public void agregarPedido(Pedido p){
        listaPedidos.agregarGenerico(p.getIdPedido(),p);
    }
    /// FUNCIONES AGREGACION POR JSON
    public  void JSONAgregarCliente(String IDUsuario, String nombre, String email, String contrasena, int edad, double fondos, boolean estado){
        Cliente cliente = new Cliente(IDUsuario, nombre, email, contrasena, edad, fondos, estado);
        listaCliente.agregarGenerico(cliente.getIdUsuario(),cliente);
    }
    public void JSONAgregarAdmin(String IDUsuario, String nombre, String email, String contrasena){
        Admin admin = new Admin(IDUsuario,nombre,email,contrasena);
        listaAdmin.agregarGenerico(admin.getIdUsuario(),admin);
    }
    public void JSONAgregarProducto(String idProducto,String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion, int stock){
        Producto producto = new Producto(idProducto, nombreProducto, precio, categoriaProducto, descripcion, stock);
        listaProductos.agregarGenerico(producto.getIdProducto(), producto);
    }


    public void mostrarListaCliente(){
        System.out.println(" \n───────────     CLIENTES      ───────────");
        listaCliente.mostrar();
        System.out.println("────────────────────────────────────────────\n");
    }
    public  void mostrarListaAdmin(){
        System.out.println("───────────    ADMINS   ───────────");
        listaAdmin.mostrar();
        System.out.println("────────────────────────────────────────────\n");
    }
    public void mostrarListaProducto(){
        System.out.println("\n───────────  PRODUCTOS  ───────────");
        listaProductos.mostrar();
        System.out.println("────────────────────────────────────────────-\n");
    }

    public void mostrarListaProductosPorCategoria(){
        System.out.println("\n───────────    PRODUCTOS DISPONIBLES   ───────────");
        for(CategoriaProducto categoria: CategoriaProducto.values()){
            System.out.println("\n─────────" + categoria + "─────────");
            boolean hayProductos = false;

            for(Producto p : listaProductos.getListaMapGenerica().values()){
                if(p.getCategoriaProducto() == categoria){
                    hayProductos = true;
                    System.out.println("ID: " + p.getIdProducto() + ", Nombre: " + p.getNombreProducto() +
                            ", precio: $" + p.getPrecio());
                }
            }

            if(!hayProductos){
                System.out.println("No hay productos en esta categoria...");
            }
        }
        System.out.println("\n");
    }

    public void mostrarListaPedido()throws ListasVaciasEx {
        if(listaPedidos.isListaVacia()){
            throw new ListasVaciasEx("la lista esta vacia...");
        }
        System.out.println("\n───────────  PEDIDOS ───────────");
        for(Pedido p : listaPedidos.getListaMapGenerica().values()){
            System.out.println("IDPedido: " + p.getIdPedido() + ", IDCliente" + p.getIdCliente() +", Fecha: " + p.getFecha()+", Total: " + p.getMontoTotal()+", Estado: " + p.getEstado() );
        }
    }

    public ClaseGenericaMap<Admin> getListaAdmin() {
        return listaAdmin;
    }

    public ClaseGenericaMap<Cliente> getListaCliente() {
        return listaCliente;
    }

    public ClaseGenericaMap<Producto> getListaProductos() {
        return listaProductos;
    }

    public ClaseGenericaMap<Pedido> getListaPedidos() {
        return listaPedidos;
    }


    public boolean existeCliente(String nombre){
        for(Cliente cliente : listaCliente.getListaMapGenerica().values()){
            if(cliente.getNombre().equalsIgnoreCase(nombre)){
                return true;
            }
        }
        return false;
    }

    public void agregarCliente(){
        Scanner sc = new Scanner(System.in);
        boolean flag = false;
        System.out.println("───────────  Agregar Nuevo Cliente  ───────────");
        System.out.println("Ingrese nombre:");
        String nombre = sc.nextLine();
        System.out.println("Ingrese email: ");
        String email = sc.nextLine();
        System.out.println("Ingrese contraseña: ");
        String contrasena = sc.nextLine();
        int edad;
        do {
            System.out.println("La edad debe ser entre 18 y 99 años.");
            edad = leerEnteroSeguro(sc, "Ingrese la edad: ");
            if(edad >= 18 && edad <= 99){
                flag = true;
            }
        }while(!flag);
        agregarCliente(nombre,email,contrasena,edad);
        System.out.println(Etiquetas.EXITO +"Cliente agregado!");
    }
    /// Metodos para ALTA / BAJA CLIENTE MEDIANTE IDUsuario
    public void darDeBajaCliente(String id)throws ElementoInexistenteEx{
        cambiarEstadoCliente(id,false);
    }
    public void darDeAltaCliente(String id)throws  ElementoInexistenteEx{
        cambiarEstadoCliente(id,true);
    }
    public void cambiarEstadoCliente(String id, boolean activo)throws ElementoInexistenteEx{
        Cliente cliente = listaCliente.getPorId(id);
        if(cliente == null){
            throw new ElementoInexistenteEx("La id del cliente no existe...");
        }
        if(activo){
            cliente.activar();
            System.out.println(Etiquetas.EXITO +"Cliente dado de alta!");
        }else{
            cliente.desactivar();
            System.out.println(Etiquetas.EXITO +"Cliente dado de baja!");
        }
    }


    /// METODO PARA AGREGAR UN PRODUCTO
    ///
    public void agregarProducto(){

        Scanner sc = new Scanner(System.in);
        System.out.println("───────────  Agregar Nuevo Producto  ───────────");
        System.out.println("Ingrese nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Ingrese precio: ");
        double precio = leerDoubleSeguro(sc,"Ingrese precio: ");

        CategoriaProducto categoriaProducto;

        do {
            System.out.println("Categorias disponibles: COMIDA, BEBIDAS, ALIMENTOS, POSTRES");
            System.out.println("Ingrese categoria: ");
            String categoriaString = sc.nextLine();
            try {
                categoriaProducto = CategoriaProducto.valueOf(categoriaString.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println(Etiquetas.INFO+"Categoria invalida.");
                categoriaProducto = null;
            }
        }while(categoriaProducto == null);
        System.out.println("Ingrese descripcion: ");
        String descripcion = sc.nextLine();
        int stock = leerEnteroSeguro(sc,"Ingrese stock: ");

        agregarProducto(nombre,precio,categoriaProducto,descripcion,stock);
        System.out.println(Etiquetas.EXITO +"Producto Agregado con exito!");
    }

    /// METODO PARA MODIFICAR DATOS DE UN PRODUCTO
    public void modificarProducto(String id)throws ElementoInexistenteEx {
        if(!listaProductos.existeId(id)){
            throw new ElementoInexistenteEx("La id del producto no existe...");
        }
        Producto productoModificar = listaProductos.getPorId(id);
        Scanner sc = new Scanner(System.in);
        boolean terminar = true;

        System.out.println("─────────── Modificar Producto ───────────");

        while(terminar){
            System.out.println("1. Cambiar Nombre");
            System.out.println("2. Cambiar Precio");
            System.out.println("3. Cambiar Categoria");
            System.out.println("4. Cambiar Descripcion");
            System.out.println("5. Cambiar stock");
            System.out.println("6. Terminar");
            int opcion = leerEnteroSeguro(sc,"Opcion: ");

            switch (opcion){
                case 1:
                    System.out.println("Ingrese nombre: ");
                    String nombre = sc.nextLine();
                    productoModificar.setNombreProducto(nombre);
                    break;
                case 2:
                    double precio = leerDoubleSeguro(sc,"Ingrese Precio: ");
                    productoModificar.setPrecio(precio);
                    break;
                case 3:

                    CategoriaProducto categoriaProducto;

                    do {
                        System.out.println("Categorias disponibles: COMIDA, BEBIDAS, ALIMENTOS, POSTRES");
                        System.out.println("Ingrese categoria: ");
                        String categoriaString = sc.nextLine();
                        try {
                            categoriaProducto = CategoriaProducto.valueOf(categoriaString.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println(Etiquetas.INFO+"Categoria invalida.");
                            categoriaProducto = null;
                        }
                    }while(categoriaProducto == null);
                    productoModificar.setCategoriaProducto(categoriaProducto);
                    break;
                case 4:
                    System.out.println("Ingrese descripcion: ");
                    String descripcion = sc.nextLine();
                    productoModificar.setDescripcion(descripcion);
                    break;
                case 5:
                    int stock = leerEnteroSeguro(sc,"Ingrese stock: ");
                    productoModificar.setStock(stock);
                    break;
                case 6:
                    terminar = false;
                    System.out.println(Etiquetas.INFO+"Aplicando cambios");
                    break;
                default:
                    System.out.println(Etiquetas.INFO+"Opcion invalida");
                    break;
            }

        }
        System.out.println(Etiquetas.EXITO +"Cambios exitosos!");
    }

    /// AGREGAR O QUITAR STOCK
    public void agregarStock(String  id, int cantidad)throws ElementoInexistenteEx{
        Producto producto = listaProductos.getPorId(id);
        if(producto == null){
            throw new ElementoInexistenteEx("El producto no existe...");
        }
        producto.setStock(producto.getStock() + cantidad);
        System.out.println(Etiquetas.EXITO +"Stock agregado con exito!");
    }
    public void quitarStock(String id, int cantidad)throws ElementoInexistenteEx{
        Producto producto = listaProductos.getPorId(id);
        if(producto == null){
            throw new ElementoInexistenteEx("El producto no existe...");
        }
        /// Si la resta da menor a 0, seteamos en 0 el stock. para que el stock no sea negativo
        if((producto.getStock() - cantidad) < 0){
            producto.setStock(0);
        }
        producto.setStock(producto.getStock() - cantidad);
        System.out.println(Etiquetas.EXITO +"Stock descontado con exito!");
    }






    ///


    /// Loguearse
    public void loguearse()throws ListasVaciasEx {

        if(listaCliente.isListaVacia() && listaAdmin.isListaVacia()){
            throw new ListasVaciasEx("Listas vacias...");
        }
        String decision;
        Scanner sc = new Scanner(System.in);

        do {
            /// Ingresamos los datos para loguearse y se verifican las credenciales en el sistema
            System.out.println("─────────── LOGIN ───────────");
            System.out.println("Ingrese su email:");
            String email = sc.nextLine(); ///Uso trim para evitar espacios y errores en el buffer
            System.out.println("Ingrese su contraseña: ");
            String contrasena = sc.nextLine();
            System.out.println("─────────────────────────────────\n");
            /// Si las credenciales coinciden con las de un admin, tenemo accesos a ese objeto admin
            for (Admin admin : listaAdmin.getListaMapGenerica().values()) {
                if (admin.getEmail().equals( email) && admin.getContrasena().equals( contrasena)) {

                    System.out.println(Etiquetas.INFO+"Logueado como Admin!");
                    Menu.menuAdmin(admin, this);
                    subirJSON();
                    return;
                }
            }
            /// Si las credenciales coinciden con las de un cliente, tenemos acceso a ese objeto cliente
            for (Cliente cliente : listaCliente.getListaMapGenerica().values()) {
                if (cliente.getEmail().equals(email) && cliente.getContrasena().equals(contrasena)) {

                    System.out.println(Etiquetas.INFO+"Logueado como Cliente!");

                    Menu.menuCliente(this , cliente);
                    subirJSON();
                    return;
                }
            }
            System.out.println(Etiquetas.INFO+" Usuario no encontrado...");
            System.out.println("Desea intentarlo de nuevo? si/no: ");
            decision = sc.nextLine().trim();
        }while(decision.equalsIgnoreCase("si"));
    }


    /// REGISTRARSE -> Crea un nuevo cliente con los datos ingresados y lo mete en la listaCliente
    public void registrarse()throws ElementoDuplicadoEx {
        Scanner sc = new Scanner(System.in);
        boolean flag = false;
        System.out.println("─────────── Registrarse ───────────");
        System.out.println("Ingrese su nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Ingrese su email: ");
        String email = sc.nextLine();
        System.out.println("Ingrese su contrasena: ");
        String contrasena = sc.nextLine();
        int edad;
        do {
            System.out.println("La edad debe ser entre 18 y 99 años.");
            edad = leerEnteroSeguro(sc, "Ingrese su edad: ");
            if(edad >= 18 && edad <= 99){
                flag = true;
            }
        }while(!flag);
        if (existeCliente(nombre)) {
            throw new ElementoDuplicadoEx("El usuario a registrar ya existe en la lista...");
        }
        agregarCliente(nombre, email, contrasena, edad);
        System.out.println(Etiquetas.EXITO +"Cliente registrado!");
        subirJSON();
    }













    /// Metodo para que lo unico que pueda leer sea un entero
    /// De lo contrario maneja la excepcion que puede arrojar
    /// Se queda en bucle hasta que no ingreses el mismo tipo de dato
    public int leerEnteroSeguro(Scanner sc, String mensaje) {
        int numero;

        while (true) {
            System.out.print(mensaje);
            String input = sc.nextLine();

            try {
                numero = Integer.parseInt(input);
                return numero;  // Si es válido, lo retorna
            } catch (NumberFormatException e) {
                System.out.println(Etiquetas.ERROR + " Ingrese un número válido.");
            }
        }
    }
    /// Metodo para que lo unico que pueda leer sea un double
    /// De lo contrario maneja la excepcion que puede arrojar
    /// Se queda en bucle hasta que no ingreses el mismo tipo de dato
    public double leerDoubleSeguro(Scanner sc, String mensaje) {
        double numero;

        while (true) {
            System.out.print(mensaje);
            String input = sc.nextLine();

            try {
                numero = Double.parseDouble(input);
                return numero;  // Si es válido, lo retorna
            } catch (NumberFormatException e) {
                System.out.println(Etiquetas.ERROR + " Ingrese un número válido.");
            }
        }
    }






    public JSONArray pedidosToJSON() {
        JSONArray jsonArrayPedidos = new JSONArray();

        for (String id : listaPedidos.getListaMapGenerica().keySet()) {
            Pedido pedido = listaPedidos.getPorId(id);
            JSONObject jsonPedido = new JSONObject();

            jsonPedido.put("idPedido", pedido.getIdPedido());
            jsonPedido.put("montoTotal", pedido.getMontoTotal());
            jsonPedido.put("idCliente", pedido.getIdCliente());
            jsonPedido.put("fecha", pedido.getFecha());
            jsonPedido.put("estado", pedido.getEstado().toString());

            JSONArray jsonItems = new JSONArray();

            for (Map.Entry<String, ItemCarrito> entry : pedido.getItems().entrySet()) {
                ItemCarrito item = entry.getValue();
                JSONObject jsonItem = new JSONObject();

                jsonItem.put("idProducto", item.getProducto().getIdProducto());
                jsonItem.put("cantidad", item.getCantidad());
                jsonItem.put("precioUnitario", item.getProducto().getPrecio());
                jsonItem.put("subtotal", item.getProducto().getPrecio() * item.getCantidad());

                jsonItems.put(jsonItem);
            }
            jsonPedido.put("items", jsonItems);
            jsonArrayPedidos.put(jsonPedido);
        }
        return jsonArrayPedidos;
    }




    /**
     * Convierte la lista de productos a JSONArray para exportar a JSON.
     *
     */


    public JSONArray productosToJSON(){
        JSONArray jsonArrayProductos = new JSONArray();
        for(String id : listaProductos.getListaMapGenerica().keySet()){
            JSONObject jsonObjectProducto = new JSONObject();
            jsonObjectProducto.put("idProducto",listaProductos.getPorId(id).getIdProducto());
            jsonObjectProducto.put("nombreProducto",listaProductos.getPorId(id).getNombreProducto());
            jsonObjectProducto.put("precio",listaProductos.getPorId(id).getPrecio());
            jsonObjectProducto.put("categoriaProducto",listaProductos.getPorId(id).getCategoriaProducto());
            jsonObjectProducto.put("descripcion",listaProductos.getPorId(id).getDescripcion());
            jsonObjectProducto.put("stock",listaProductos.getPorId(id).getStock());
            jsonArrayProductos.put(jsonObjectProducto);
        }
        return jsonArrayProductos;
}
    /**
     * Convierte la lista de productos a JSONArray para exportar a JSON.
     *
     */

        public JSONArray adminsToJson(){
            JSONArray jsonArrayAdmins = new JSONArray();
            for(Admin a : listaAdmin.getListaMapGenerica().values()){
                JSONObject jsonObjectAdmin = new JSONObject();
                jsonObjectAdmin.put("IDUsuario",a.getIdUsuario());
                jsonObjectAdmin.put("nombre",a.getNombre());
                jsonObjectAdmin.put("email",a.getEmail());
                jsonObjectAdmin.put("contraseña",a.getContrasena());
                jsonArrayAdmins.put(jsonObjectAdmin);
            }
            return jsonArrayAdmins;
        }

    public  JSONArray ClientesToJSON(){
        JSONArray jsonArrayClientes = new JSONArray() ;
        for (Cliente c : listaCliente.getListaMapGenerica().values()){
            JSONObject jsonObjectCliente = new JSONObject();
            jsonObjectCliente.put("IDUsuario",c.getIdUsuario());
            jsonObjectCliente.put("nombre",c.getNombre());
            jsonObjectCliente.put("email",c.getEmail());
            jsonObjectCliente.put("contraseña",c.getContrasena());
            jsonObjectCliente.put("edad",c.getEdad());
            jsonObjectCliente.put("fondos",c.getFondos());
            jsonObjectCliente.put("estado",c.isEstado());

            jsonArrayClientes.put(jsonObjectCliente);
        }
        return jsonArrayClientes;
    }
    public JSONObject AllToJSON(JSONArray jsonCliente, JSONArray jsonAdmin){
        JSONObject JSONObjectAll = new JSONObject();
        JSONObjectAll.put("clientes",jsonCliente);
        JSONObjectAll.put("admins",jsonAdmin);
        return JSONObjectAll;
    }

    public void subirJSON(){
            JSONUtiles.uploadJSON(AllToJSON(ClientesToJSON(),adminsToJson()),archivoCompleto);
    }
    public void subirJSONProductos(){
            JSONUtiles.uploadJSON(productosToJSON(),archivoProductos);
    }
    public void subirJSONPedidos(){
            JSONUtiles.uploadJSON(pedidosToJSON(),archivoPedidos);
    }



}
