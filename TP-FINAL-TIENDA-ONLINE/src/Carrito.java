import java.util.ArrayList;
import java.util.List;

public class Carrito {

  private Producto producto;

    ///-- CONSTRUCTO --

    public Carrito(List<Producto> productos) {
        this.producto = producto;

    }

    ///-- GETTERS SETTERS --
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    ///-- METODOS --
    @Override
    public String toString() {
        return "Carrito{" +
                "producto=" + producto +
                '}';
    }


    public void MostrarCarrito (){
        System.out.println(producto);
    }

    public int Cant(){
    return 1;
    }
}
