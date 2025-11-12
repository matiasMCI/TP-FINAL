import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<Producto> productos;
    private int cantProductos;
    private double precioTotal;

    ///-- CONSTRUCTO --

    public Carrito(List<Producto> productos, int cantProductos, double precioTotal) {
        this.productos = productos;
        this.cantProductos = cantProductos;
        this.precioTotal = precioTotal;
    }

    ///-- GETTERS SETTERS --

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public int getCantProductos() {
        return cantProductos;
    }

    public void setCantProductos(int cantProductos) {
        this.cantProductos = cantProductos;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    ///-- toSTRING --
    @Override
    public String toString() {
        return "Carrito{" +
                "productos=" + productos +
                ", cantProductos=" + cantProductos +
                ", precioTotal=" + precioTotal +
                '}';
    }
///-- METODOS --


}
