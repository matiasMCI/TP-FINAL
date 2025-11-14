package Clases;

public class HistorialDePedidos {

    private  Pedido pedido ;


    public HistorialDePedidos (Pedido pedido){
        this.pedido = pedido;
    }

    public Pedido getPedido() {
        return pedido;
    }


    @Override
    public String toString() {
        return "HistorialDePedidos{" +
                "pedido=" + pedido +
                '}';
    }
}
