package GestorPedidos;

public class NotificadorService {
    public void enviarCorreo(String email, String asunto, String mensaje) {
        System.out.println("Enviando correo a " + email + "...");
        System.out.println("Asunto: " + asunto);
        System.out.println("Cuerpo: " + mensaje);
    }
}