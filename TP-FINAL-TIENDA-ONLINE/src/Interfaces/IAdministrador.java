package Interfaces;

import org.json.JSONArray;
import sistema.SistemaTienda;
import Enums.CategoriaProducto;

public interface IAdministrador {


    public void agregarCliente(SistemaTienda sistema);
    public void darDeBajaCliente(SistemaTienda sistema,String id);
    public void darDeAltaCliente(SistemaTienda sistema,String id);
    public void cambiarEstadoCliente(SistemaTienda sistema, String id, boolean activo);
    public void agregarProducto(SistemaTienda sistema);
    public void modificarProducto(SistemaTienda sistema,String id);
    public void agregarStock(SistemaTienda sistema,String  id, int cantidad);
    public void quitarStock(SistemaTienda sistema,String id, int cantidad);
    public void modificarCliente(SistemaTienda sistema, String idCliente);
    public void buscarClienteMasFrecuente(SistemaTienda sistema);


}
