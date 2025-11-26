package sistema;

import Clases.*;
import Enums.CategoriaProducto;
import Excepciones.ElementoDuplicadoEx;
import Excepciones.ElementoInexistenteEx;
import Excepciones.ListasVaciasEx;
import JSONUtiles.JSONUtiles;
import Menu.Menu;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public SistemaTienda(JSONObject data, JSONArray data2) throws JSONException {
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
            String email = sc.nextLine().trim(); ///Uso trim para evitar espacios y errores en el buffer
            System.out.println("Ingrese su contraseña: ");
            String contrasena = sc.nextLine().trim();
            System.out.println("─────────────────────────────────\n");
            /// Si las credenciales coinciden con las de un admin, tenemo accesos a ese objeto admin
            for (Admin admin : listaAdmin.getListaMapGenerica().values()) {
                if (admin.getEmail().equals( email) && admin.getContrasena().equals( contrasena)) {

                    System.out.println("Logueado como Admin!");
                    Menu.menuAdmin(admin, this);
                    subirJSON();
                    return;
                }
            }
            /// Si las credenciales coinciden con las de un cliente, tenemos acceso a ese objeto cliente
            for (Cliente cliente : listaCliente.getListaMapGenerica().values()) {
                if (cliente.getEmail().equals(email) && cliente.getContrasena().equals(contrasena)) {

                    System.out.println("Logueado como Cliente!");

                    Menu.menuCliente(this , cliente);
                    subirJSON();
                    return;
                }
            }
            System.out.println(" Usuario no encontrado...");
            System.out.println("Desea intentarlo de nuevo? si/no: ");
            decision = sc.nextLine().trim();
        }while(decision.equalsIgnoreCase("si"));
    }


    /// REGISTRARSE -> Crea un nuevo cliente con los datos ingresados y lo mete en la listaCliente
    public void registrarse()throws ElementoDuplicadoEx {
        Scanner sc = new Scanner(System.in);

        System.out.println("─────────── Registrarse ───────────");
        System.out.println("Ingrese su nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Ingrese su email: ");
        String email = sc.nextLine();
        System.out.println("Ingrese su contrasena: ");
        String contrasena = sc.nextLine();
        System.out.println("Ingrese su edad: ");
        int edad = sc.nextInt();
        if (existeCliente(nombre)) {
            throw new ElementoDuplicadoEx("El usuario a registrar ya existe en la lista...");
        }
        agregarCliente(nombre, email, contrasena, edad);
        System.out.println("Cliente registrado!");
        subirJSON();
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
                jsonObjectAdmin.put("IDUsuario",a.getIdAdmin());
                jsonObjectAdmin.put("nombre",a.getNombre());
                jsonObjectAdmin.put("email",a.getEmail());
                jsonObjectAdmin.put("contraseña",a.getContrasena());
                jsonArrayAdmins.put(jsonObjectAdmin);
            }
            return jsonArrayAdmins;
        }

    public  JSONArray ClientesToJSON(){
        JSONArray PULLclientes = new JSONArray() ;
        for (Cliente c : listaCliente.getListaMapGenerica().values()){
            JSONObject jsonObjectCliente = new JSONObject();
            jsonObjectCliente.put("IDUsuario",c.getIdUsuario());
            jsonObjectCliente.put("nombre",c.getNombre());
            jsonObjectCliente.put("email",c.getEmail());
            jsonObjectCliente.put("contraseña",c.getContrasena());
            jsonObjectCliente.put("edad",c.getEdad());
            jsonObjectCliente.put("fondos",c.getFondos());
            jsonObjectCliente.put("estado",c.isEstado());

            PULLclientes.put(jsonObjectCliente);
        }
     return PULLclientes;
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



}
