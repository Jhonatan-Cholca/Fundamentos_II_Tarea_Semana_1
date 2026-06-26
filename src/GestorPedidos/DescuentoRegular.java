package GestorPedidos;

public class DescuentoRegular implements IDescuentoStrategy {
    public double calcularDescuento(double subtotal) { return subtotal * 0.05; }
}