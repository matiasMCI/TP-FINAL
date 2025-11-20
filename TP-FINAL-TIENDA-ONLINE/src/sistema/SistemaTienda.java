package sistema;

import Clases.*;
import Enums.CategoriaProducto;
import Excepciones.ElementoDuplicadoEx;
import Excepciones.ElementoInexistenteEx;
import JSONUtiles.JSONUtiles;
import org.json.JSONArray;
import org.json.JSONObject;

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
