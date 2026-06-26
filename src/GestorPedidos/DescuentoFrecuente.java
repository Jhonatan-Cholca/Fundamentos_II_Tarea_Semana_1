package GestorPedidos;

public class DescuentoFrecuente implements IDescuentoStrategy {
    public double calcularDescuento(double subtotal) { return subtotal * 0.10; }
}