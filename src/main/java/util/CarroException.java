package util;

public class CarroException extends Exception {
    public CarroException(String message) {
        super(message);
    }
    public static CarroException notFound() {
        return new CarroException("Carro não encontrado");
    }
}
