package Interfaces;

import org.json.JSONArray;
import sistema.SistemaTienda;
import Enums.CategoriaProducto;

public interface IAdministrador {

    public void DarDeBajaCliente(SistemaTienda sistema,int id);
    public void DarDeAltaCliente(SistemaTienda sistema,int id);
    public void ModificarCliente(SistemaTienda sistema,int id);
    public void ModificarProducto(SistemaTienda sistemaTienda, String id);
    public void DarDeBajaProducto(SistemaTienda sistema,String id);
    public void DarDeAltaProducto(SistemaTienda sistema,String idProducto, String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion);
    public void ClienteMasFrecuente(SistemaTienda sistema);
    public void VerClientes(SistemaTienda sistema);
    public void VerListaDeProductos(SistemaTienda sistema);
    public void VerPedidos(SistemaTienda sistema);
    public void cargarJsonProducto(SistemaTienda sistema );
    public void cargarJsonClientes(SistemaTienda sistema);

}
