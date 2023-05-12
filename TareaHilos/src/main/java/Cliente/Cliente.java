package Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente implements ActionListener {
    JFrame frame;
    JButton button;
    JPanel panel;

    boolean activo = false;

    public Cliente() {
        frame = new JFrame("Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("Encender");
        button.addActionListener(this);
        button.setActionCommand("encender");

        panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("encender")) {
            button.setText("Apagar");
            button.setActionCommand("apagar");
            activo = true;
        } else {
            button.setText("Encender");
            button.setActionCommand("encender");
            activo = false;
        }

        enviarEstadoServidor();
    }

    private void enviarEstadoServidor() {
        try {
            Socket socket = new Socket("localhost", 12345); // Conectar al servidor

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Enviar estado al servidor
            out.println(activo ? "encendido" : "apagado");

            // Recibir mensaje de confirmación del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String confirmacion = in.readLine();
            System.out.println("Mensaje de confirmación del servidor: " + confirmacion);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente();
    }
}
