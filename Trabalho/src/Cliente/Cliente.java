package Cliente;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class Cliente {

    public static void main(String[] args) {
        String servidor = "localhost"; 
        int porta = 50000;             

        try (Socket socket = new Socket(servidor, porta);
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
             
            System.out.println("Conectado ao servidor.");

            String stringParaEnviar = JOptionPane.showInputDialog("Digite a string a ser ordenada:");
            if (stringParaEnviar == null || stringParaEnviar.trim().isEmpty()) {
                System.out.println("Nenhuma string foi enviada.");
                return;
            }

            System.out.println("Enviando string: " + stringParaEnviar);
            saida.println(stringParaEnviar);

            String resposta = entrada.readLine();
            System.out.println("Resposta do servidor: " + resposta);
            JOptionPane.showMessageDialog(null, resposta);
        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        }
    }
}
