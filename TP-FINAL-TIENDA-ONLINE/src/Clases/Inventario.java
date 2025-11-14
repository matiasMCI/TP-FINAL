package Clases;

public class Inventario {

    private int IDpedido = 1;
    private int stock  ;


    ///-- CONSTRUCTOR --

    public Inventario(int IDpedido, int stock) {
        this.IDpedido = IDpedido;
        this.stock = stock;
    }

    ///-- GETTERS AND SETTERS --

    public int getIDpedido() {
        return IDpedido;
    }

    public void setIDpedido(int IDpedido) {
        this.IDpedido = IDpedido;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    ///-- toSTRING --
    @Override
    public String toString() {
        return "Clases.Inventario{" +
                "IDpedido=" + IDpedido +
                ", stock=" + stock +
                '}';
    }
}
