package GestorPedidos;

import java.util.*;
import java.io.*;
import java.sql.*;

public class GestorPedidos  {

private NotificadorService notificador = new NotificadorService();
private IPedidoRepository repo = new SqlPedidoRepository();

public static void main(String[] args) {
        GestorPedidos gestor = new GestorPedidos();
        List<String> productos = Arrays.asList("Laptop", "Mouse");
        List<Double> precios = Arrays.asList(800.0, 20.0);
        List<Integer> cantidades = Arrays.asList(1, 2);
        gestor.procesarPedido("Stalyn Catucuamba", "deimos@email.com", productos, precios, cantidades, "VIP");
        System.out.println("Ejecución finalizada.");
    }

public GestorPedidos() {}

public void procesarPedido(String nombreCliente, String emailCliente,
List<String> nombresProductos,
List<Double> preciosProductos,
List<Integer> cantidades,
String tipoCliente) {
  if (nombreCliente == null || nombreCliente.trim().isEmpty() || emailCliente == null || !emailCliente.contains("@")) {
        System.out.println("Error: datos de cliente invalidos");
        return;
        }

        double subtotal = 0;
        for (int i = 0; i < nombresProductos.size(); i++) {
            subtotal += preciosProductos.get(i) * cantidades.get(i);
        }

        double descuento = 0;
        if (tipoCliente.equals("VIP")) descuento = subtotal * 0.20;
        else if (tipoCliente.equals("FRECUENTE")) descuento = subtotal * 0.10;
        else if (tipoCliente.equals("REGULAR")) descuento = subtotal * 0.05;

        double impuesto = (subtotal - descuento) * 0.12;
        double total = subtotal - descuento + impuesto;

        repo.guardar(nombreCliente, total);

        try (FileWriter writer = new FileWriter("factura_" + nombreCliente + ".txt")) {
            writer.write("FACTURA\nCliente: " + nombreCliente + "\nTOTAL: $" + total + "\n");
        } catch (IOException e) {
        e.printStackTrace();
        }

        notificador.enviarCorreo(emailCliente, "Confirmacion de pedido", "Estimado " + nombreCliente + 
        ", su pedido por $" + total + " ha sido procesado.");
}


public void cancelarPedido(String nombreCliente, String emailCliente, int idPedido) {
repo.eliminar(idPedido);
notificador.enviarCorreo(emailCliente, "Cancelacion de pedido", 
    "Estimado " + nombreCliente + ", su pedido #" + idPedido + " ha sido cancelado.");
}
}
