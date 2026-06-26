package GestorPedidos;

public class SinDescuento implements IDescuentoStrategy {
    public double calcularDescuento(double subtotal) { return 0; }
}