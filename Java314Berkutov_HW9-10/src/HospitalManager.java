import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Scanner;

public class HospitalManager {
    private Connection connection;
    private Scanner sc;

    public HospitalManager(Connection connection, Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }

    public void start() throws SQLException {

        while (true) {
            System.out.println("\n***Добро пожаловать***\nВыберите действие:");
            System.out.println("1. Добавить пациента");
            System.out.println("2. Просмотр пациентов");
            System.out.println("3. Просмотр врачей");
            System.out.println("4. Запись на прием");
            System.out.println("5. Информация о приемах");
            System.out.println("0. выход");
            System.out.print("Выбарите действие: ");

            int choice = Integer.parseInt(sc.nextLine());
            System.out.println();
            switch (choice) {
                case 0:
                    System.out.println("До свидания.");
                    return;
                case 1:
                    addPatient();
                    break;
                case 2:
                    patientList();
                    break;
                case 3:
                    doctorList();
                    break;
                case 4:
                    newAppointment();
                    break;
                case 5:
                    appointmentList();
                    break;
                default:
                    System.err.println("Не верный выбор!");
            }

        }

    }

    private int addPatientToDB(String firstName, String lastName, String birthDate, String gender) throws SQLException {
        int nextId = 0;
        String getIdQuery = "select max(id) from patients";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getIdQuery);
            if (!resultSet.next()) {
                throw new SQLException("Error getting next id");
            }
            nextId = resultSet.getInt(1) + 1;
        }

        String addQuery = "insert into patients (id, first_name, last_name, birth_date, gender)" +
                " values (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);

            String[] dateArr = birthDate.split("\\.");
            LocalDate localDate = LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
            preparedStatement.setDate(4, Date.valueOf(localDate));

            preparedStatement.setString(5, (gender.equalsIgnoreCase("М") ? "Male" : "Female"));
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("Пациент добавлен");
            } else {
                System.out.println("Не удалось добавить пациента");
            }
        }
        return nextId;
    }

    private void addPatient() throws SQLException {
        System.out.println("Введите данные нового пациента.");
        System.out.print("Имя: ");
        String firstName = sc.nextLine();
        System.out.print("Фамилия: ");
        String lastName = sc.nextLine();
        System.out.print("Дата рождения (dd.mm.yyyy): ");
        String birthDate = sc.nextLine();
        System.out.print("Пол (М/Ж): ");
        String gender = sc.nextLine();
        System.out.println();

        addPatientToDB(firstName, lastName, birthDate, gender);

    }

    private void appointmentList() throws SQLException {
        String query = "select p.first_name, p.last_name, p.gender, a.appointment_date, d.first_name, d.last_name, d.specialization\n" +
                "from appointments a\n" +
                "left outer join doctors d on d.id=a.doctor_id\n" +
                "left outer join patients p on p.id=a.patient_id\n";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                System.out.println("** Текущие приемы **");
                System.out.println("+---------------------+---------------------+---------------------+---------------------+");
                System.out.println("|       Пациент       |      Дата приема    |         Врач        |    Специальность    |");
                System.out.println("+---------------------+---------------------+---------------------+---------------------+");
                do {
                    System.out.printf("| %s| %s| %s| %s|\n",
                            String.format("%-20s", rs.getString(1) + " " + rs.getString(2)),
                            String.format("%-20s", new SimpleDateFormat("dd.MM.yyyy hh:mm").format(rs.getTimestamp(4))),
                            String.format("%-20s", rs.getString(5) + " " + rs.getString(6)),
                            String.format("%-20s", rs.getString(7))
                    );
                    System.out.println("+---------------------+---------------------+---------------------+---------------------+");
                } while (rs.next());
            }
        }
    }

    private void patientList() {
        String query = "select first_name, last_name, birth_date, gender from patients";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                System.out.println("** Список пациентов **");
                System.out.println("+---------------------+---------------------+---------------------+---------------------+");
                System.out.println("|       Фамилия       |          Имя        |   Дата рождения     |         Пол         |");
                System.out.println("+---------------------+---------------------+---------------------+---------------------+");
                do {
                    System.out.printf("| %s| %s| %s| %s|\n",
                            String.format("%-20s", rs.getString(2)),
                            String.format("%-20s", rs.getString(1)),
                            String.format("%-20s", new SimpleDateFormat("dd.MM.yyyy").format(rs.getTimestamp(3))),
                            String.format("%-20s", rs.getString(4))
                    );
                    System.out.println("+---------------------+---------------------+---------------------+---------------------+");
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void doctorList() {
        String query = "select * from doctors";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                System.out.println("** Список врачей **");
                System.out.println("+---------------------+---------------------+---------------------+");
                System.out.println("|    Идентификатор    |          Имя        |   Специализация     |");
                System.out.println("+---------------------+---------------------+---------------------+");
                do {
                    System.out.printf("| %s| %s| %s|\n",
                            String.format("%-20s", rs.getString(1)),
                            String.format("%-20s", rs.getString(2) + " " + rs.getString(3)),
                            String.format("%-20s", rs.getString(4))
                    );
                    System.out.println("+---------------------+---------------------+---------------------+");
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void newAppointment() throws SQLException {
        System.out.println("Введите идентификатор врача: ");

        int docId = 0;
        String docName = "";
        String specialization = "";
        try (Statement statement = connection.createStatement()) {
            docId = Integer.parseInt(sc.nextLine());
            String queryDoc = String.format("select * from doctors where id=%d", docId);
            ResultSet resultSet = statement.executeQuery(queryDoc);
            if (!resultSet.next()) {
                System.out.println("Врач с таким идентификатором не найден");
                return;
            }
            docName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
            specialization = resultSet.getString("specialization");
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }

        if (docId == 0) {
            System.err.println("Ошибка при выборе врача");
            return;
        }

        System.out.printf("Запись к врачу %s (%s)\n", docName, specialization);
        System.out.println("Введите данные пациента.");

        System.out.print("Имя: ");
        String firstName = sc.nextLine();
        System.out.print("Фамилия: ");
        String lastName = sc.nextLine();
        System.out.print("Дата рождения (dd.mm.yyyy): ");
        String birthDate = sc.nextLine();
        System.out.print("Пол (М/Ж): ");
        String gender = sc.nextLine();

        System.out.println();

        Integer patId;
        connection.setAutoCommit(false);
        patId = findPatinentId(firstName, lastName, birthDate);
        if (patId == null) {
            patId = addPatientToDB(firstName, lastName, birthDate, gender);
        }

        System.out.print("Введите дуту приема (dd.mm.yyyy): ");
        String appDate = sc.nextLine();
        System.out.print("Введите время (hh:mm): ");
        String appTime = sc.nextLine();


        String queryAppoint = "insert into appointments (doctor_id, patient_id, appointment_date)" +
                " values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryAppoint)) {
            preparedStatement.setInt(1, docId);
            preparedStatement.setInt(2, patId);


            String[] dateArr = appDate.split("\\.");
            String[] timeArr = appTime.split(":");

            Timestamp localDateTime = Timestamp.valueOf(LocalDateTime.of(
                    Integer.parseInt(dateArr[2]),
                    Integer.parseInt(dateArr[1]),
                    Integer.parseInt(dateArr[0]),
                    Integer.parseInt(timeArr[0]),
                    Integer.parseInt(timeArr[1]), 0, 0));
            preparedStatement.setTimestamp(3, localDateTime);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Запись прошла усешно");
                connection.commit();
            } else {
                connection.rollback();
                System.out.println("Ошибка регистрации приема");
            }
        }
        connection.setAutoCommit(true);

    }

    private Integer findPatinentId(String firstName, String lastName, String birthDate) throws SQLException {
        String getIdQuery = "select id from patients where first_name=? and last_name=? and birth_date=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getIdQuery)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            String[] dateArr = birthDate.split("\\.");
            LocalDate localDate = LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
            preparedStatement.setDate(3, Date.valueOf(localDate));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return resultSet.getInt(1);
        }
    }

}
