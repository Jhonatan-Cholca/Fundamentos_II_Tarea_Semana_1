package GestorPedidos;

import java.sql.*;

public class SqlPedidoRepository implements IPedidoRepository {
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=tienda;encrypt=true;trustServerCertificate=true;";
    private final String user = "sa";
    private final String password = "L0rdD3!m0z";

    public void guardar(String cliente, double total) {
        String sql = "INSERT INTO pedidos (cliente, total) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente);
            pstmt.setDouble(2, total);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en BD: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pedidos WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en BD: " + e.getMessage());
        }
    }
}