package com.weekone;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDate;

public class Main {


    public static void main(String[] args) {

       // ArrayListVsLinked.compare();

        /*Counter counter = new Counter();
        counter.start();
        counter.join(10000);
        counter.interrupt();*/


        new Thread(() -> {


            MySingleTon ms1 = MySingleTon.getInstance();
            MySingleTon ms2 = MySingleTon.getInstance();
            System.out.println(ms1==ms2);
        }).start();

        MySingleTon ms3 = MySingleTon.getInstance();

        //printOneTo100(50);

        Parent.print();
        Child.print();
        Child p = new Child();
        p.print();

        String a = "11";
        String b = "1";
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';

            sb.append(sum % 2);
            carry = sum / 2;
        }

        if (carry != 0) sb.append(carry);

        System.out.println(sb.reverse());


        BigInteger i1 = new BigInteger("11", 2);
        System.out.println(i1);
        BigInteger i2 = new BigInteger("1", 2);

        System.out.println(i1.add(i2).toString(2));

       /* int[] arr = new int[]{1,1,2,3};


        int[] arr2 = Arrays.copyOf(arr,12);


        int len = arr.length;
        int current = 0;
        for (int i = 1; i < len; i++){
            if (arr[i] > arr[current]){
                arr[++current] = arr[i];
            }
        }
        System.out.println(current);
        System.out.println(Arrays.toString(arr));




        Map<String, String> map= new HashMap<>();

        new Thread(()->{
            printOneTo100(1);
        }).start();

*/





    }

    private static int printOneTo100(int num){

        System.out.print(num+" ");
        if (num == 20) return num;
        return printOneTo100(++num);
    }

    public boolean isValidBirthDate(String dateStr) {
        LocalDate ld;
        try {
            ld = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            if (ld.isAfter(LocalDate.now().minusDays(1)) || ld.isBefore(LocalDate.of(1900, 01, 01))){
                return false;
            }
        }catch(DateTimeParseException e){
            return false;
        }
        return true;
    }


    public class UserService {
        private Map<Long, User> users = new HashMap<>();

        public Optional<User> findUserById(Long id) {
            return Optional.ofNullable(users.get(id)); // может вернуть null
        }
    }

    public class NotificationService {
        private UserService userService;

        public void sendWelcomeEmail(Long userId) {
            Optional<User> user = userService.findUserById(userId);
            user.ifPresent(u -> {
                Email email = new Email(u.getEmail(), "Welcome!");
                EmailClient.send(email);
            });
        }
    }


}

class OrderService {

    public String read(String path) throws IOException {
        try(BufferedReader r = new BufferedReader(new FileReader(path))){
            return r.readLine();
        }
    }

    public String findMostExpensiveProduct(List<Order> orders) {


        try {
            FileReader fileReader = new FileReader("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return orders.stream()
                .filter(Objects::nonNull)
                .map(Order::getProducts)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Product::getPrice))
                .map(Product::getName)
                .orElse("No products");
    }
}

// Упрощённые классы:
class Order {
    private List<Product> products;
    public List<Product> getProducts() {
        return products;
    }
    // конструктор, сеттеры...
}

class Product {
    private String name;
    private BigDecimal price;
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    // конструктор...
}


class Parent{
    static void print(){
        System.out.println("print parent");
    }
}

class Child extends Parent{

}