package sistema;

import Clases.*;
import Enums.CategoriaProducto;
import Enums.ESTADO_CLIENTE;
import Enums.TIPO_CLIENTE;
import Excepciones.ElementoDuplicadoEx;
import Excepciones.IDdontExistEX;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.classfile.attribute.PermittedSubclassesAttribute;
import java.util.List;
import java.util.Map;

/**
 * Clase SistemaTienda.
 * Gestiona los productos, clientes, pedidos y administradores de la tienda.
 * Permite agregar, eliminar y consultar elementos, y exportar datos a JSON.
 *
 */

public class SistemaTienda {

    private claseGenericaTest<Producto> listaDeProductos;
    private ClaseGenerica<Cliente> listaDeClientes;
    private ClaseGenerica<Pedido> listaDePedido;
    private ClaseGenerica<Administrador> listaDeAdministradores;
    private static final String JSONproductos = "productos";
    private static final String JSONclientes = "clientes";

    /**
     * Constructor. Inicializa las listas de productos, clientes, pedidos y administradores.
     */

    public SistemaTienda() {
        listaDeClientes = new ClaseGenerica<>();
        listaDePedido = new ClaseGenerica<>();
        listaDeProductos = new claseGenericaTest<>();
        listaDeAdministradores = new ClaseGenerica<>();
    }

    ///-- METODOS --

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


    /**
     * Agrega un Cliente en la tienda.
     *
     *
     */
    public void agregarCliente(Cliente cliente) {
        listaDeClientes.agregarItem(cliente);
    }

    public void agregarCliente(String email, String contrasena, String nombre, int edad, TIPO_CLIENTE tipoCliente, ESTADO_CLIENTE estado, double domicilio , double fondos){
        Cliente cliente = new Cliente(email,contrasena,nombre,edad,tipoCliente,estado,domicilio,fondos);
        agregarCliente(cliente);
    }

    /**
     * Agrega un Administrador en la tienda.
     *
     *
     */

    public void agregarAdministrador(Administrador administrador) {
        listaDeAdministradores.agregarItem(administrador);
    }


    public void agregarAdministrador(String nombre,String email, String contrasena,  int edad, int idAdmin){
        Administrador admin = new Administrador(email, contrasena, nombre, edad, idAdmin);
        agregarAdministrador(admin);
    }




    public void VerListaDeTodosLosProductos(){

        listaDeProductos.mostrarGenericoTest();

    }

    public void VerListaDeTodosLosClientes(){

        System.out.println(listaDeClientes.verGenerico());

    }

    public void VerListaDeTodosLosPedidos(){

        System.out.println(listaDePedido.verGenerico());

    }


    public Administrador getAdminPorID(int id){
        for(Administrador admin : listaDeAdministradores.getListaGenerica()){
            if(admin.getIDusuario() == id){
                return admin;
            }
        }
        return null;
    }
    public Cliente getClientePorID(int id){
        for(Cliente cliente : listaDeClientes.getListaGenerica()){
            if(cliente.getIDusuario() == id){
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
        return listaDeClientes;
    }

    public ClaseGenerica<Pedido> getListaDePedido() {
        return listaDePedido;
    }

    public ClaseGenerica<Administrador> getListaDeAdministradores() {
        return listaDeAdministradores;
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

    public  JSONArray ClientesToJSON(){
        JSONArray PULLclientes = new JSONArray() ;



        for (Cliente c : listaDeClientes.getListaGenerica()){
            JSONObject clientesOBJ = new JSONObject();
            clientesOBJ.put("nombre",c.getNombre());
            clientesOBJ.put("edad",c.getEdad());
            clientesOBJ.put("Tipo de cliente",c.getTipoCliente());
            clientesOBJ.put("estado del cliente",c.getEstado());
            clientesOBJ.put("domicilio ",c.getDomicilio());
            clientesOBJ.put("fondos del cliente",c.getFondos());
            clientesOBJ.put("email",c.getEmail());
            clientesOBJ.put("contrasena",c.getContrasena());



            PULLclientes.put(clientesOBJ);
        }

     return PULLclientes;
    }



    public void subirJSONProducto(){

        JSONUtiles.uploadJSON(productosToJSON(),JSONproductos);
    }
    public void subirJSONClientes(){

        JSONUtiles.uploadJSON(ClientesToJSON(),JSONclientes);
    }

}
