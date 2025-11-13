package Interfaces;

public interface IAdministrador {

    public void DarDeBajaCliente(int id);
    public void DarDeAltaCliente(int id);
    public void ModificarCliente(int id);
    public void ModificarProducto();
    public void DarDeBajaProducto();
    public void DarDeAltaProducto();
    public void MostrarMasVendidos();
    public void ClienteMasFrecuente();
    public void VerClientes();
    public void VerListaDeProductos();
    public void VerPedidos();
    public void VerComprobantes();

}
