import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();

            System.out.println(inetAddress);

            System.out.println(inetAddress.getHostAddress());
            System.out.println(inetAddress.getCanonicalHostName());
            System.out.println(inetAddress.getHostName());

            System.out.println("---------");

            inetAddress = InetAddress.getByName("www.yandex.com");
            System.out.println(inetAddress);
            System.out.println(inetAddress.getHostAddress());
            System.out.println(inetAddress.getCanonicalHostName());
            System.out.println(inetAddress.getHostName());


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}