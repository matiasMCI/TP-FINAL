import java.util.List;

public class SistemaTienda {

    private ClaseGenerica<Producto> listaDeProductos;
    private ClaseGenerica<Cliente> listaDeClientes;
    private ClaseGenerica<Pedido>listaDePedido;
    private ClaseGenerica<Administrador> listaDeAdministradores;

    public SistemaTienda() {
        listaDeClientes = new ClaseGenerica<>();
        listaDePedido = new ClaseGenerica<>();
        listaDeProductos = new ClaseGenerica<>();
        listaDeAdministradores = new ClaseGenerica<>();
    }




    ///-- METODOS --


    public void agregarProducto(Producto producto) {
        listaDeProductos.agregarItem(producto);
    }

    public void agregarProducto(String idProducto, String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion){
        Producto producto = new Producto(idProducto, nombreProducto, precio, categoriaProducto, descripcion);
        agregarProducto(producto);
    }

    public void agregarCliente(Cliente cliente) {
        listaDeClientes.agregarItem(cliente);
    }

    public void agregarCliente( String email, String contrasena, String nombre, String edad, TIPO_CLIENTE tipoCliente, ESTADO_CLIENTE estado){
        Cliente cliente = new Cliente(email, contrasena, nombre, edad, tipoCliente, estado);
        agregarCliente(cliente);
    }

    public void agregarAdministrador(Administrador administrador) {
        listaDeAdministradores.agregarItem(administrador);
    }



    public void agregarAdministrador(String email, String contrasena, String nombre, int edad, int idAdmin){
        Administrador admin = new Administrador(email, contrasena, nombre, edad, idAdmin);
        agregarAdministrador(admin);
    }


    public void VerListaDeTodosLosProductos(){

        System.out.println(listaDeProductos.verGenerico());

    }

    public void VerListaDeTodosLosClientes(){

        System.out.println(listaDeClientes.verGenerico());

    }

    public void VerListaDeTodosLosPedidos(){

        System.out.println(listaDePedido.verGenerico());

    }





}
