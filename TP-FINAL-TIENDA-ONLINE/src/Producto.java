public class Producto {

    private String idProducto;
    private String nombreProducto;
    private double precio;
    private CategoriaProducto categoriaProducto;
    private String descripcion;


    ///-- CONSTRUCTOR --
    public Producto(String idProducto, String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.categoriaProducto = categoriaProducto;
        this.descripcion = descripcion;
    }
    ///-- GETTERS SETTERS --
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
///-- METODOS --



}
