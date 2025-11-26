package Clases;
/**
 * Representa un ítem dentro del carrito de compras de un cliente.
 *
 * <p>Un {@code ItemCarrito} contiene un producto y la cantidad seleccionada
 * por el cliente. Esta clase se utiliza dentro del carrito para gestionar
 * los productos que se desean comprar.</p>
 *
 * <p>No valida stock ni lógica comercial; simplemente almacena la referencia
 * al producto y la cantidad solicitada.</p>
 */
public class ItemCarrito {
    /** Producto que el cliente desea comprar. */
    private Producto producto;
    /** Cantidad seleccionada del producto. */
    private int cantidad;

    /**
     * Crea un nuevo ítem para el carrito de compras.
     *
     * @param producto producto que se agregará al carrito
     * @param cantidad cantidad seleccionada del producto
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                '}';
    }
}
