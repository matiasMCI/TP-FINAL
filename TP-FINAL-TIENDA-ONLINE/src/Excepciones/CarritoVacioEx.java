package Excepciones;

public class CarritoVacioEx extends RuntimeException {
    public CarritoVacioEx(String message) {
        super(message);
    }
}
