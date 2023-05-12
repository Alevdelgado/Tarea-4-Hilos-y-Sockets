package Servidor;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Puerto de escucha

            System.out.println("Servidor esperando conexiones...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Esperar a que un cliente se conecte
                System.out.println("Cliente conectado");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String estado = in.readLine(); // Recibir estado del cliente

                // Realizar acciones según el estado recibido
                String confirmacion = "";
                if (estado.equals("encendido")) {
                    confirmacion = "El servidor ha recibido la solicitud y ha cambiado el estado a encendido.";
                } else if (estado.equals("apagado")) {
                    confirmacion = "El servidor ha recibido la solicitud y ha cambiado el estado a apagado.";
                }

                // Enviar mensaje de confirmación al cliente
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(confirmacion);

                clientSocket.close();
                System.out.println("Cliente desconectado");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
