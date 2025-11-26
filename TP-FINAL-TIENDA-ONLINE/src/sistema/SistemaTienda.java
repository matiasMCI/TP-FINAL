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
 *
 * Gestiona la tienda online completa incluyendo:
 * clientes, administradores, productos y pedidos.
 *
 * <p>Proporciona métodos para:
 * <ul>
 *     <li>Agregar, modificar y eliminar clientes, admins y productos.</li>
 *     <li>Registrar clientes y loguearse en el sistema.</li>
 *     <li>Realizar compras y mantener historial de pedidos.</li>
 *     <li>Exportar e importar datos desde/hacia JSON.</li>
 * </ul>
 * </p>
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
     * Constructor por defecto.
     * Inicializa las listas de administradores, clientes, productos y pedidos vacías.
     */

    public SistemaTienda() {
        listaAdmin = new ClaseGenericaMap<>();
        listaCliente = new ClaseGenericaMap<>();
        listaProductos = new ClaseGenericaMap<>();
        listaPedidos = new ClaseGenericaMap<>();
    }

    /**
     * Constructor para deserializar la tienda desde JSON.
     * Llena listas de clientes, admins, productos y pedidos.
     * Actualiza los contadores de ID para nuevos objetos.
     *
     * @param data JSONObject con clientes y admins.
     * @param data2 JSONArray con productos.
     * @param dataPedido JSONArray con pedidos.
     * @throws JSONException Si ocurre un error de parsing JSON.
     */
    public SistemaTienda(JSONObject data, JSONArray data2, JSONArray dataPedido) throws JSONException {
        // Inicializa listas desde JSON y actualiza contadores de ID
         this();

        // Variable para guardar al maximo ID
        int maxID = 0;
        String id;

        // Llenado de lista Admins
        JSONArray jsonArrayAdmins = data.getJSONArray("admins");
        for(int i = 0; i < jsonArrayAdmins.length(); i++){
            JSONObject jsonObjectAdmin = jsonArrayAdmins.getJSONObject(i);
            id = jsonObjectAdmin.getString("IDUsuario");
            int num = Integer.parseInt(id.replace("ID",""));
            if(num > maxID){
                maxID = num;
            }
            JSONAgregarAdmin(jsonObjectAdmin.getString("IDUsuario"),jsonObjectAdmin.getString("nombre"),
                    jsonObjectAdmin.getString("apellido"),
                    jsonObjectAdmin.getString("email"),jsonObjectAdmin.getString("contraseña"));
        }

        // Llenado de listas Clientes
        JSONArray jsonArrayClientes = data.getJSONArray("clientes");
        for(int i = 0; i < jsonArrayClientes.length();i++) {
            JSONObject jsonObjectCliente = jsonArrayClientes.getJSONObject(i);

            // Va agarrando ID y buscando el maximo
            id = jsonObjectCliente.getString("IDUsuario");
            int num = Integer.parseInt(id.replace("ID", ""));
            if (num > maxID) {
                maxID = num;
            }
            JSONAgregarCliente(jsonObjectCliente.getString("IDUsuario"),
                    jsonObjectCliente.getString("nombre"),jsonObjectCliente.getString("apellido") ,jsonObjectCliente.getString("email"),
                    jsonObjectCliente.getString("contraseña"), jsonObjectCliente.getInt("edad"),
                    jsonObjectCliente.getDouble("fondos"), jsonObjectCliente.getBoolean("estado")
            );
        }
        // Encuentro el maximo ID y seteo mi contador en el siguiente a ese
        Usuario.setContador(maxID+1);

        //
        int maxIDProducto = 0;
        String idProducto;
        // Lista productos
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
                    jsonObjectProducto.getInt("stock"),
                    jsonObjectProducto.getBoolean("estado"));
        }
        Producto.setContador(maxIDProducto+1);

//  ////////////////////////


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


    /**
     * Agrega un nuevo cliente a la tienda.
     *
     * @param nombre Nombre del cliente.
     * @param email Email del cliente.
     * @param contrasena Contraseña del cliente.
     * @param edad Edad del cliente.
     */
    public void agregarCliente(String nombre, String apellido,String email, String contrasena, int edad){
        Cliente cliente = new Cliente(nombre, apellido,email, contrasena, edad);
        listaCliente.agregarGenerico(cliente.getIdUsuario(),cliente);
    }
    /**
     * Agrega un nuevo administrador a la tienda.
     *
     * @param nombre Nombre del administrador.
     * @param email Email del administrador.
     * @param contrasena Contraseña del administrador.
     */
    public void agregarAdmin(String nombre,String apellido, String email, String contrasena){
        Admin admin = new Admin(nombre, apellido,email, contrasena);
        listaAdmin.agregarGenerico(admin.getIdUsuario(),admin);
    }
    /**
     * Agrega un producto nuevo a la tienda.
     *
     * @param nombreProducto Nombre del producto.
     * @param precio Precio del producto.
     * @param categoriaProducto Categoría del producto.
     * @param descripcion Descripción del producto.
     * @param stock Stock disponible.
     * @throws ElementoDuplicadoEx Si ya existe un producto con el mismo ID.
     */
    public void agregarProducto(String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion, int stock)throws  ElementoDuplicadoEx{
        Producto producto = new Producto(nombreProducto, precio, categoriaProducto, descripcion, stock);
        listaProductos.agregarGenerico(producto.getIdProducto(),producto);
    }
    /**
     * Agrega un pedido a la lista general de pedidos y al historial del cliente.
     *
     * @param p Pedido a agregar.
     */
    public void agregarPedido(Pedido p){
        listaPedidos.agregarGenerico(p.getIdPedido(),p);
    }
    /// FUNCIONES AGREGACION POR JSON
    /**
     * Agrega un cliente desde datos JSON.
     */
    public  void JSONAgregarCliente(String IDUsuario, String nombre,String apellido, String email, String contrasena, int edad, double fondos, boolean estado){
        Cliente cliente = new Cliente(IDUsuario, nombre, apellido,email, contrasena, edad, fondos, estado);
        listaCliente.agregarGenerico(cliente.getIdUsuario(),cliente);
    }
    /**
     * Agrega un administrador desde datos JSON.
     */
    public void JSONAgregarAdmin(String IDUsuario, String nombre, String apellido,String email, String contrasena){
        Admin admin = new Admin(IDUsuario,nombre,apellido,email,contrasena);
        listaAdmin.agregarGenerico(admin.getIdUsuario(),admin);
    }
    /**
     * Agrega un producto desde datos JSON.
     */
    public void JSONAgregarProducto(String idProducto,String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion, int stock, boolean estado){
        Producto producto = new Producto(idProducto, nombreProducto, precio, categoriaProducto, descripcion, stock, estado);
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
                if(p.getCategoriaProducto() == categoria && p.getEstado()){
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


    public boolean existeCliente(String nombre, String apellido){
        for(Cliente cliente : listaCliente.getListaMapGenerica().values()){
            if(cliente.getNombre().equalsIgnoreCase(nombre) && cliente.getApellido().equalsIgnoreCase(apellido)){
                return true;
            }
        }
        return false;
    }

    /**
     * Permite que un usuario inicie sesión como cliente o admin.
     *
     * @throws ListasVaciasEx Si no hay clientes ni admins registrados.
     */
    public void loguearse()throws ListasVaciasEx {

        if(listaCliente.isListaVacia() && listaAdmin.isListaVacia()){
            throw new ListasVaciasEx("Listas vacias...");
        }
        String decision;
        Scanner sc = new Scanner(System.in);

        do {
            // Ingresamos los datos para loguearse y se verifican las credenciales en el sistema
            System.out.println("─────────── LOGIN ───────────");
            System.out.println("Ingrese su email:");
            String email = sc.nextLine(); ///Uso trim para evitar espacios y errores en el buffer
            System.out.println("Ingrese su contraseña: ");
            String contrasena = sc.nextLine();
            System.out.println("─────────────────────────────────\n");
            // Si las credenciales coinciden con las de un admin, tenemo accesos a ese objeto admin
            for (Admin admin : listaAdmin.getListaMapGenerica().values()) {
                if (admin.getEmail().equals( email) && admin.getContrasena().equals( contrasena)) {

                    System.out.println(Etiquetas.INFO+"Logueado como Admin!");
                    Menu.menuAdmin(admin, this);
                    subirJSON();
                    return;
                }
            }
            // Si las credenciales coinciden con las de un cliente, tenemos acceso a ese objeto cliente
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


    /**
     * Registra un cliente desde la entrada del usuario.
     *
     * @throws ElementoDuplicadoEx Si el cliente ya existe.
     */
    public void registrarse()throws ElementoDuplicadoEx {
        Scanner sc = new Scanner(System.in);
        boolean flag = false;
        System.out.println("─────────── Registrarse ───────────");
        System.out.println("Ingrese su nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.println("Ingrese su apellido: ");
        String apellido = sc.nextLine().trim();
        System.out.println("Ingrese su email: ");
        String email = sc.nextLine().trim();
        System.out.println("Ingrese su contrasena: ");
        String contrasena = sc.nextLine().trim();
        int edad;
        do {
            System.out.println("La edad debe ser entre 18 y 99 años.");
            edad = leerEnteroSeguro(sc, "Ingrese su edad: ");
            if(edad >= 18 && edad <= 99){
                flag = true;
            }
        }while(!flag);
        if (existeCliente(nombre, apellido)) {
            throw new ElementoDuplicadoEx("El usuario a registrar ya existe en la lista...");
        }
        agregarCliente(nombre,apellido, email, contrasena, edad);
        System.out.println(Etiquetas.EXITO +"Cliente registrado!");
        subirJSON();
    }



    /**
     * Lee un entero seguro desde la consola.
     *
     * @param sc Scanner para lectura.
     * @param mensaje Mensaje a mostrar.
     * @return Entero ingresado por el usuario.
     */
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
    /**
     * Lee un double seguro desde la consola.
     *
     * @param sc Scanner para lectura.
     * @param mensaje Mensaje a mostrar.
     * @return Double ingresado por el usuario.
     */
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

    /**
     * Convierte la lista de pedidos a JSONArray para exportar a JSON.
     *
     * @return JSONArray con todos los pedidos y sus items.
     */
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
     * @return JSONArray con todos los productos.
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
            jsonObjectProducto.put("estado",listaProductos.getPorId(id).getEstado());
            jsonArrayProductos.put(jsonObjectProducto);
        }
        return jsonArrayProductos;
    }
    /**
     * Convierte la lista de admins a JSONArray para exportar a JSON.
     *
     * @return JSONArray con todos los administradores.
     */
        public JSONArray adminsToJson(){
            JSONArray jsonArrayAdmins = new JSONArray();
            for(Admin a : listaAdmin.getListaMapGenerica().values()){
                JSONObject jsonObjectAdmin = new JSONObject();
                jsonObjectAdmin.put("IDUsuario",a.getIdUsuario());
                jsonObjectAdmin.put("nombre",a.getNombre());
                jsonObjectAdmin.put("apellido",a.getApellido());
                jsonObjectAdmin.put("email",a.getEmail());
                jsonObjectAdmin.put("contraseña",a.getContrasena());
                jsonArrayAdmins.put(jsonObjectAdmin);
            }
            return jsonArrayAdmins;
        }

    /**
     * Convierte la lista de clientes a JSONArray para exportar a JSON.
     *
     * @return JSONArray con todos los clientes.
     */
    public  JSONArray ClientesToJSON(){
        JSONArray jsonArrayClientes = new JSONArray() ;
        for (Cliente c : listaCliente.getListaMapGenerica().values()){
            JSONObject jsonObjectCliente = new JSONObject();
            jsonObjectCliente.put("IDUsuario",c.getIdUsuario());
            jsonObjectCliente.put("nombre",c.getNombre());
            jsonObjectCliente.put("apellido",c.getApellido());
            jsonObjectCliente.put("email",c.getEmail());
            jsonObjectCliente.put("contraseña",c.getContrasena());
            jsonObjectCliente.put("edad",c.getEdad());
            jsonObjectCliente.put("fondos",c.getFondos());
            jsonObjectCliente.put("estado",c.isEstado());

            jsonArrayClientes.put(jsonObjectCliente);
        }
        return jsonArrayClientes;
    }

    /**
     * Combina clientes y admins en un JSONObject completo.
     *
     * @param jsonCliente JSONArray de clientes.
     * @param jsonAdmin JSONArray de admins.
     * @return JSONObject con clientes y admins.
     */
    public JSONObject AllToJSON(JSONArray jsonCliente, JSONArray jsonAdmin){
        JSONObject JSONObjectAll = new JSONObject();
        JSONObjectAll.put("clientes",jsonCliente);
        JSONObjectAll.put("admins",jsonAdmin);
        return JSONObjectAll;
    }

    /**
     * Sube la tienda completa a JSON.
     */
    public void subirJSON(){
            JSONUtiles.uploadJSON(AllToJSON(ClientesToJSON(),adminsToJson()),archivoCompleto);
    }
    /**
     * Sube la lista de productos a JSON.
     */
    public void subirJSONProductos(){
            JSONUtiles.uploadJSON(productosToJSON(),archivoProductos);
    }

    /**
     * Sube la lista de pedidos a JSON.
     */
    public void subirJSONPedidos(){
            JSONUtiles.uploadJSON(pedidosToJSON(),archivoPedidos);
    }



}
