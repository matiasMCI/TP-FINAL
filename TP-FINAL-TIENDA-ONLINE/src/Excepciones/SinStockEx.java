package Excepciones;

public class SinStockEx extends RuntimeException {
    public SinStockEx(String message) {
        super(message);
    }
}
