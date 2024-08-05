package clientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private static final int PORT = 5555;
    private String message = "Клиент %d отправил сообщение\n\t ";
    private String connection = "Клиент %d закрыл соединение";

    private Socket socket;
    private int num;

    public void setSocket(int num, Socket socket){
        this.num = num;
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String line = "";
            boolean getNextQuote = false;
            while (true){
                line = dis.readUTF();

                System.out.printf(message, num);
                System.out.println(line);
                System.out.println("Отправляю обратно...");

                getNextQuote = line.equalsIgnoreCase("yes");

                if(getNextQuote){
                    dos.writeUTF(Quote.getRandomQuote());
                } else {
                    dos.writeUTF("quit");
                }
                dos.flush();

                System.out.println();
                if (!getNextQuote){
                    socket.close();
                    System.out.printf(connection, num);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            try {
                int i = 0;
                InetAddress inetAddress = InetAddress.getByName("localhost");
                serverSocket = new ServerSocket(PORT, 0, inetAddress);
                System.out.println("Сервер запущен");
                while (true) {
                    Socket addSocket = serverSocket.accept();
                    System.err.println("\n\n Клиент принят");

                    new Server().setSocket(i++, addSocket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
