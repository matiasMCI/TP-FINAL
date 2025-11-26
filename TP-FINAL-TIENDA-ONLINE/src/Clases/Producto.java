package Clases;

import Enums.CategoriaProducto;


/**
 * La clase Producto representa un artículo disponible en una tienda.
 *  Contiene información sobre el id, nombre y precio del producto.
 *
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


    ///-- CONSTRUCTOR --
    public Producto( String nombreProducto, double precio, CategoriaProducto categoriaProducto, String descripcion, int stock) {
        this.idProducto = idGenerador();
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.categoriaProducto = categoriaProducto;
        this.descripcion = descripcion;
        this.stock = stock;
        estado = true;
    }
    /// CONSTRUCTOR JSON
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
    public void activar(){
        estado = true;
    }
    public void desactivar(){
        estado = false;
    }
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
