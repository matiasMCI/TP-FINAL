import Enums.CategoriaProducto;
import Enums.ESTADO_CLIENTE;
import Enums.TIPO_CLIENTE;

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


    public void agregarProducto(Producto producto) {
        String id = producto.getIdProducto();
        listaDeProductos.add(id,producto);
    }

    public void agregarProducto(String idProducto, String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion){
        Producto producto = new Producto(idProducto, nombreProducto, precio, categoriaProducto, descripcion);

        agregarProducto(producto);
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


    public claseGenericaTest<Producto> getListaDeProductos() {
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
