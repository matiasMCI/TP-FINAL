package Clases;

import Enums.CategoriaProducto;


/**
 * Representa un producto disponible dentro de la tienda.
 * Cada producto posee un ID único, nombre, precio, categoría,
 * descripción, stock y un estado que indica si está disponible
 * para los clientes.
 *
 * <p>El estado {@code true} significa que el producto está de alta
 * y visible para los clientes; {@code false} indica que está dado de baja
 * y no debe mostrarse al público.</p>
 */

public class Producto {
    private static int contador = 1;

    private String  idProducto;
    private String nombreProducto;
    private double precio;
    private CategoriaProducto categoriaProducto;
    private String descripcion;
    private int stock;
    private Boolean estado;



    /**
     * Constructor principal para crear un producto dentro del sistema.
     *
     * @param nombreProducto      nombre del producto
     * @param precio              precio del producto
     * @param categoriaProducto   categoría del producto
     * @param descripcion         descripción breve
     * @param stock               stock inicial
     */

    public Producto( String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion, int stock) {
        this.idProducto = idGenerador();
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.categoriaProducto = categoriaProducto;
        this.descripcion = descripcion;
        this.stock = stock;
        estado = true;
    }
    /**
     * Constructor utilizado al cargar productos desde archivos JSON.
     *
     * @param idProducto          ID ya generado
     * @param nombreProducto      nombre del producto
     * @param precio              precio
     * @param categoriaProducto   categoría
     * @param descripcion         descripción
     * @param stock               cantidad disponible
     * @param estado              estado del producto
     */
    public Producto(String idProducto, String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion, int stock, boolean estado){
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.categoriaProducto = categoriaProducto;
        this.descripcion = descripcion;
        this.stock = stock;
        this.estado = estado;
    }
    ///-- GETTERS SETTERS --
    public static void setContador(int valor){
        contador = valor;
    }

    public static int getContador() {
        return contador;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * Genera un ID único con formato: {@code ID###}.
     *
     * @return ID autogenerado
     */
    public String idGenerador(){
        return String.format("ID" + "%03d",contador++);
    }
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getStock() {
        return stock;
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

    public Boolean getEstado() {
        return estado;
    }
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    /** Activa el producto (lo hace visible a clientes). */
    public void activar(){
        estado = true;
    }
    /** Desactiva el producto (lo oculta para clientes). */
    public void desactivar(){
        estado = false;
    }
    /**
     * Retorna un mensaje legible del estado.
     *
     * @return cadena indicando si está de alta o baja
     */
    public String conversorEstado(){
        if(getEstado()){
            return "Producto dado de alta";
        }else{
            return "Producto dado de baja";
        }
    }

    /// -- toSTRING --
    @Override
    public String toString() {
        return
                "idProducto='" + idProducto + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precio=" + precio +
                ", categoriaProducto=" + categoriaProducto +
                ", descripcion='" + descripcion + '\'' +
                ", stock=" + stock +
                ", estado=" + conversorEstado() +
                '}';
    }


///-- METODOS --



}
