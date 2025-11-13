import Enums.CategoriaProducto;
import Enums.ESTADO_CLIENTE;
import Enums.TIPO_CLIENTE;
import Excepciones.ElementoDuplicadoEx;
import Excepciones.IDdontExistEX;

public class SistemaTienda {

    private static claseGenericaTest<Producto> listaDeProductos;
    private static ClaseGenerica<Cliente> listaDeClientes;
    private ClaseGenerica<Pedido>listaDePedido;
    private ClaseGenerica<Administrador> listaDeAdministradores;

    public SistemaTienda() {
        listaDeClientes = new ClaseGenerica<>();
        listaDePedido = new ClaseGenerica<>();
        listaDeProductos = new claseGenericaTest<>();
        listaDeAdministradores = new ClaseGenerica<>();
    }





    ///-- METODOS --


    public static void agregarProducto(Producto producto)throws ElementoDuplicadoEx {
        String id = producto.getIdProducto();
        listaDeProductos.add(id,producto);
    }

    public static void agregarProducto(String idProducto, String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion)throws  ElementoDuplicadoEx{
        Producto producto = new Producto(idProducto, nombreProducto, precio, categoriaProducto, descripcion);
        agregarProducto(producto);
    }
    public static void eliminarProducto(String idProducto)throws IDdontExistEX {
       listaDeProductos.remove(idProducto);
    }

    public void agregarCliente(Cliente cliente) {
        listaDeClientes.agregarItem(cliente);
    }

    public void agregarCliente(String email, String contrasena, String nombre, int edad, TIPO_CLIENTE tipoCliente, ESTADO_CLIENTE estado, double domicilio , double fondos){
        Cliente cliente = new Cliente(email,contrasena,nombre,edad,tipoCliente,estado,domicilio,fondos);
        agregarCliente(cliente);
    }

    public void agregarAdministrador(Administrador administrador) {
        listaDeAdministradores.agregarItem(administrador);
    }



    public void agregarAdministrador(String nombre,String email, String contrasena,  int edad, int idAdmin){
        Administrador admin = new Administrador(email, contrasena, nombre, edad, idAdmin);
        agregarAdministrador(admin);
    }


    public static void VerListaDeTodosLosProductos(){

        listaDeProductos.mostrarGenericoTest();

    }

    public static void VerListaDeTodosLosClientes(){

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


    public static claseGenericaTest<Producto> getListaDeProductos() {
        return listaDeProductos;
    }

    public static ClaseGenerica<Cliente> getListaDeClientes() {
        return listaDeClientes;
    }

    public ClaseGenerica<Pedido> getListaDePedido() {
        return listaDePedido;
    }

    public ClaseGenerica<Administrador> getListaDeAdministradores() {
        return listaDeAdministradores;
    }



}
