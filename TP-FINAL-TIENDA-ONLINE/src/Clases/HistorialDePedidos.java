package Clases;

public class HistorialDePedidos {

    private  Pedido pedido ;

    /**
     * La clase historia de pedidos sirve para guardar todos los pedidos
     * que se realizen cuando el cliente confirme un pago ,
     * esta consta de un toStrign para luego podedr verlos
     *
     */
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
