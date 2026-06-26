package GestorPedidos;

public class DescuentoVip implements IDescuentoStrategy {
    public double calcularDescuento(double subtotal) { return subtotal * 0.20; }
}