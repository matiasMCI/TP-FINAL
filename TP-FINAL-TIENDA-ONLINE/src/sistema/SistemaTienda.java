package sistema;

import Clases.*;
import Enums.CategoriaProducto;
import Enums.ESTADO_CLIENTE;
import Enums.TIPO_CLIENTE;
import Excepciones.ElementoDuplicadoEx;
import Excepciones.IDdontExistEX;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Clase SistemaTienda.
 * Gestiona los productos, clientes, pedidos y administradores de la tienda.
 * Permite agregar, eliminar y consultar elementos, y exportar datos a JSON.
 *
 */

public class SistemaTienda {

    private static final String archivoCompleto = "tiendaOnlineDatos";
    private static final String  archivoProductos = "tiendaOnlineProductos";




    private claseGenericaTest<Producto> listaDeProductos;
    private ClaseGenerica<Cliente> listaDeCliente;
    private ClaseGenerica<Pedido> listaDePedido;
    private ClaseGenerica<Administrador> listaDeAdmin;


    /**
     * Constructor. Inicializa las listas de productos, clientes, pedidos y administradores.
     */

    public SistemaTienda() {
        listaDeCliente = new ClaseGenerica<>();
        listaDePedido = new ClaseGenerica<>();
        listaDeProductos = new claseGenericaTest<>();
        listaDeAdmin = new ClaseGenerica<>();
    }

    ///-- METODOS --


    /// Funciones de agregarcion por menu
    public void agregarCliente(String nombre, String email, String contrasena, int edad,TIPO_CLIENTE tipoCliente, ESTADO_CLIENTE estado){
        Cliente cliente = new Cliente(nombre, email, contrasena, edad,tipoCliente,estado);
        listaDeCliente.agregarItem(cliente);
    }
    public void agregarAdmin(String nombre, String email, String contrasena){
        Administrador admin = new Administrador(nombre, email, contrasena);
        listaDeAdmin.agregarItem(admin);
    }






    /**
     * Agrega un producto a la tienda.
     *
     * Exception ElementoDuplicadoEx Si el producto ya existe.
     */
    public void agregarProducto(Producto producto)throws ElementoDuplicadoEx {

        listaDeProductos.add(producto.getIdProducto(), producto);
    }
    public void agregarProducto(String idProducto, String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion)throws  ElementoDuplicadoEx{
        Producto producto = new Producto(idProducto, nombreProducto, precio, categoriaProducto, descripcion);
        agregarProducto(producto);
    }


    /**
     * Agrega un pedido en la tienda.
     *
     * Exception ElementoDuplicadoEx Si el pedido ya existe.
     */
    public void agregarPedido(Pedido pedido)throws ElementoDuplicadoEx {
            listaDePedido.agregarItem(pedido);

    }


    public void eliminarProducto(String idProducto)throws IDdontExistEX {
       listaDeProductos.remove(idProducto);
    }


    public void VerListaDeTodosLosProductos(){

        listaDeProductos.mostrarGenericoTest();

    }

    public void VerListaDeTodosLosClientes(){

        System.out.println(listaDeCliente.verGenerico());

    }

    public void VerListaDeTodosLosPedidos(){

        System.out.println(listaDePedido.verGenerico());

    }


    public Administrador getAdminPorID(String id){
        for(Administrador admin : listaDeAdmin.getListaGenerica()){
            if(admin.getIDusuario().equals(id)){
                return admin;
            }
        }
        return null;
    }
    public Cliente getClientePorID(String id){
        for(Cliente cliente : listaDeCliente.getListaGenerica()){
            if(cliente.getIDusuario().equals(id)){
                return cliente;
            }
        }
        return null;
    }


    /**
     * Obtiene un producto por su ID.
     *
     * Exception IDdontExistEX Si no se encuentra el producto.
     */
    public Producto getProductoPorID(SistemaTienda sistemaTienda, String id)throws IDdontExistEX{
        Producto producto = sistemaTienda.getListaDeProductos().getPorId(id);
        if (producto == null ){
            throw new IDdontExistEX("El id ingresado esta vacio o es incorrecto");
        }
        return producto;
    }

    public claseGenericaTest<Producto> getListaDeProductos() {
        return listaDeProductos;
    }

    public ClaseGenerica<Cliente> getListaDeClientes() {
        return listaDeCliente;
    }

    public ClaseGenerica<Pedido> getListaDePedido() {
        return listaDePedido;
    }

    public ClaseGenerica<Administrador> getListaDeAdministradores() {
        return listaDeAdmin;
    }


    /**
     * Convierte la lista de productos a JSONArray para exportar a JSON.
     *
     */


    public JSONArray productosToJSON(){
        JSONArray jsonArrayProductos = new JSONArray();
        for(Map.Entry<String, Producto> entry : listaDeProductos.getListaGenericaTest().entrySet()){
            Producto producto = entry.getValue();
            JSONObject jsonObjectProducto = new JSONObject();
            jsonObjectProducto.put("IdProducto",producto.getIdProducto());
            jsonObjectProducto.put("nombreProducto",producto.getNombreProducto());
            jsonObjectProducto.put("precio",producto.getPrecio());
            jsonObjectProducto.put("CategoriaProducto",producto.getCategoriaProducto());
            jsonObjectProducto.put("descripcion", producto.getDescripcion());

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
            for(Administrador a : listaDeAdmin.getListaGenerica()){
                JSONObject jsonObjectAdmin = new JSONObject();
                jsonObjectAdmin.put("IDUsuario",a.getIDusuario());
                jsonObjectAdmin.put("nombre",a.getNombre());
                jsonObjectAdmin.put("email",a.getEmail());
                jsonObjectAdmin.put("contraseña",a.getContrasena());
                jsonArrayAdmins.put(jsonObjectAdmin);
            }
            return jsonArrayAdmins;
        }

    public  JSONArray ClientesToJSON(){
        JSONArray PULLclientes = new JSONArray() ;
        for (Cliente c : listaDeCliente.getListaGenerica()){
            JSONObject jsonObjectCliente = new JSONObject();
            jsonObjectCliente.put("IDUsuario",c.getIDusuario());
            jsonObjectCliente.put("nombre",c.getNombre());
            jsonObjectCliente.put("email",c.getEmail());
            jsonObjectCliente.put("contraseña",c.getContrasena());
            jsonObjectCliente.put("edad",c.getEdad());
            jsonObjectCliente.put("fondos",c.getFondos());
            jsonObjectCliente.put("tipo de cliente",c.getTipoCliente());
            jsonObjectCliente.put("domicilio ",c.getDomicilio());
            jsonObjectCliente.put("estado",c.getEstado());

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
