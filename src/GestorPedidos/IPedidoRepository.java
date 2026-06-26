package GestorPedidos;

public interface IPedidoRepository {
    void guardar(String cliente, double total);
    void eliminar(int id);
}