package GestorPedidos;

import java.util.*;
import java.io.*;
import java.sql.*;

public class GestorPedidos {
    
    public static void main(String[] args) {
        GestorPedidos gestor = new GestorPedidos();
        
        // Datos de prueba para verificar que funciona
        List<String> productos = Arrays.asList("Laptop", "Mouse");
        List<Double> precios = Arrays.asList(800.0, 20.0);
        List<Integer> cantidades = Arrays.asList(1, 2);
        
        gestor.procesarPedido("Juan Perez", "juan@email.com", productos, precios, cantidades, "VIP");
        
        System.out.println("Ejecución finalizada.");
    }
    
    
private Connection conexionBD;
public GestorPedidos() {
try {
    
String url = "jdbc:sqlserver://localhost:1433;databaseName=tienda;encrypt=true;trustServerCertificate=true;";
        String user = "sa" ;
        String password = "L0rdD3!m0z";
    
this.conexionBD = DriverManager.getConnection(url, user, password);
        System.out.println("Conexión exitosa a SQL Server.");
} catch (SQLException e) {
e.printStackTrace();
}
}
public void procesarPedido(String nombreCliente, String emailCliente,
List<String> nombresProductos,
List<Double> preciosProductos,
List<Integer> cantidades,
String tipoCliente) {
if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
System.out.println("Error: nombre de cliente invalido");
return;
}
if (emailCliente == null || !emailCliente.contains("@")) {
System.out.println("Error: email invalido");
return;
}
double subtotal = 0;
for (int i = 0; i < nombresProductos.size(); i++) {
subtotal += preciosProductos.get(i) * cantidades.get(i);
}
double descuento = 0;
if (tipoCliente.equals("VIP")) {
descuento = subtotal * 0.20;
} else if (tipoCliente.equals("FRECUENTE")) {
descuento = subtotal * 0.10;
} else if (tipoCliente.equals("REGULAR")) {
descuento = subtotal * 0.05;
} else if (tipoCliente.equals("NUEVO")) {
descuento = 0;
}
double impuesto = (subtotal - descuento) * 0.12;
double total = subtotal - descuento + impuesto;
try {
Statement stmt = conexionBD.createStatement();
String sql = "INSERT INTO pedidos (cliente, total) VALUES ('"
+ nombreCliente + "', " + total + ")";
stmt.executeUpdate(sql);
} catch (SQLException e) {
System.out.println("Error al guardar el pedido: " + e.getMessage());
}
try {
FileWriter writer = new FileWriter("factura_" + nombreCliente + ".txt");
writer.write("FACTURA\n");
writer.write("Cliente: " + nombreCliente + "\n");
for (int i = 0; i < nombresProductos.size(); i++) {
writer.write(nombresProductos.get(i) + " x" + cantidades.get(i)
+ " = $" + (preciosProductos.get(i) * cantidades.get(i)) + "\n");
}
writer.write("Subtotal: $" + subtotal + "\n");
writer.write("Descuento: $" + descuento + "\n");
writer.write("Impuesto: $" + impuesto + "\n");
writer.write("TOTAL: $" + total + "\n");
writer.close();
} catch (IOException e) {
System.out.println("Error al generar la factura: " + e.getMessage());
}
System.out.println("Enviando correo a " + emailCliente + "...");
System.out.println("Asunto: Confirmacion de pedido");
System.out.println("Cuerpo: Estimado " + nombreCliente + ", su pedido por $"
+ total + " ha sido procesado.");
System.out.println("[LOG] Pedido procesado para " + nombreCliente
+ " - Total: " + total);
}
public void cancelarPedido(String nombreCliente, String emailCliente, int idPedido) {
if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
System.out.println("Error: nombre de cliente invalido");
return;
}
if (emailCliente == null || !emailCliente.contains("@")) {
System.out.println("Error: email invalido");
return;
}
try {
Statement stmt = conexionBD.createStatement();
String sql = "DELETE FROM pedidos WHERE id = " + idPedido;
stmt.executeUpdate(sql);
} catch (SQLException e) {
System.out.println("Error al cancelar el pedido: " + e.getMessage());
}
System.out.println("Enviando correo a " + emailCliente + "...");
System.out.println("Asunto: Cancelacion de pedido");
System.out.println("Cuerpo: Estimado " + nombreCliente + ", su pedido #"
+ idPedido + " ha sido cancelado.");
}
}
