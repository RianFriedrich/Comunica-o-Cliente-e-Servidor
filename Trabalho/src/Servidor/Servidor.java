package Servidor;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Servidor {

    public static void main(String[] args) {
        int porta = 50000;  

        try (ServerSocket servidorSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado. Aguardando conexao...");

            while (true) {
                Socket socket = servidorSocket.accept();
                System.out.println("Cliente conectado.");
                new Thread(new ClienteHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClienteHandler implements Runnable {
    private Socket socket;

    public ClienteHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true)) {

            String stringRecebida = entrada.readLine();
            System.out.println("String recebida: " + stringRecebida);

            String[] elementos = stringRecebida.split(" ");
            Arrays.sort(elementos);
            String stringOrdenada = String.join(" ", elementos);

            String mensagem = "A string foi ordenada: " + stringOrdenada;
            saida.println(mensagem);
            System.out.println("Mensagem enviada ao cliente: " + mensagem);

        } catch (IOException e) {
            System.err.println("Erro ao processar cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar socket: " + e.getMessage());
            }
        }
    }
}
