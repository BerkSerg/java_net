package clientServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final int SERVER_PORT = 5555;
    private static final String SERVER_HOST = "localhost";

    public static void main(String[] args) {
        Socket socket = null;

        try{
            try{
                System.out.printf("Добро пожаловать на клиентскую сторону \nПодключение к серверу\n\t (IP адрес %s, порт %s)", SERVER_HOST, SERVER_PORT);
                InetAddress address = InetAddress.getByName(SERVER_HOST);
                socket = new Socket(address, SERVER_PORT);
                System.out.println("\nСоединение установлено");
                System.out.println("\t Адрес хоста " + socket.getInetAddress().getHostAddress() + "\t Размер буфера " + socket.getReceiveBufferSize());

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                System.out.println("Хотите получить цитату дня (yes/no)?");
                String line = br.readLine();
                while (!line.isEmpty()){
                    if(line.equalsIgnoreCase("yes") || line.equalsIgnoreCase("no")){
                        dos.writeUTF(line);
                        dos.flush();

                        line = dis.readUTF();
                        if(line.endsWith("quit")){
                            System.out.println("Спасибо за использование сервиса");
                            break;
                        }

                        System.out.println("\nСервер отправил цитату: \n\t" + line + "\n");
                    }
                    System.out.println("Хотите получить цитату дня?");
                    line = br.readLine();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } finally{
            try {
                if (socket != null) {
                   socket.close();
                }
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }
}
