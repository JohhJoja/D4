package com.dima.eliseev;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Random;

public class DB_data_generator {
    // Параметры подключения к БД
    private static final String URL = "jdbc:postgresql://localhost:5433/deeplom";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1";

    // Случайные данные
    private static final String[] LAST_NAMES = {"Иванов", "Петров", "Сидоров", "Кузнецов", "Федоров", "Егоров", "Смирнов", "Попов", "Васильев", "Морозов"};
    private static final String[] FIRST_NAMES = {"Алексей", "Дмитрий", "Егор", "Иван", "Максим", "Николай", "Олег", "Павел", "Роман", "Сергей"};
    private static final String[] MIDDLE_NAMES = {"Алексеевич", "Дмитриевич", "Егорович", "Иванович", "Максимович", "Николаевич", "Олегович", "Павлович", "Романович", "Сергеевич"};
    private static final String[] STATUS_VALUES = {"открыт", "закрыт", "ожидает"};
    private static final String[] GROUPS = {"А", "Б", "В", "Г", "Д"};

    private static final Random random = new Random();

    public static void GENERATE() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Подключение к БД успешно!");

            String sql = "INSERT INTO tickets (name1, status, code, signetTo, time, urgency, groupS) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (int i = 0; i < 1000; i++) {
                    // Генерация случайных значений
                    String name1 = getRandomFIO();
                    String status = getRandomStatus();
                    String code = "IM" + (100000 + random.nextInt(900000));
                    String signetTo = getRandomFIO();

                    // Генерация случайной даты и времени
                    String dateStr = "2024-04-" + (random.nextInt(30) + 1) + " " +
                            new DecimalFormat("00").format(random.nextInt(23) + 1) + ":" +
                            new DecimalFormat("00").format(random.nextInt(60)) + ":00";

                    Timestamp timestamp = Timestamp.valueOf(dateStr);

                    int urgency = random.nextInt(5) + 1;
                    String group = getRandomGroup();

                    // Установка параметров и выполнение запроса
                    statement.setString(1, name1);
                    statement.setString(2, status);
                    statement.setString(3, code);
                    statement.setString(4, signetTo);
                    statement.setTimestamp(5, timestamp);
                    statement.setInt(6, urgency);
                    statement.setString(7, group);

                    statement.addBatch();  // Добавляем в пакет

                    if (i % 100 == 0) { // Каждые 100 записей отправляем в БД
                        statement.executeBatch();
                        System.out.println("💾 Добавлено " + (i + 1) + " записей...");
                    }
                }
                statement.executeBatch(); // Отправляем оставшиеся записи
                System.out.println("✅ Данные успешно добавлены!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getRandomFIO() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)] + " " +
                FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] + " " +
                MIDDLE_NAMES[random.nextInt(MIDDLE_NAMES.length)];
    }

    private static String getRandomStatus() {
        return STATUS_VALUES[random.nextInt(STATUS_VALUES.length)];
    }

    private static String getRandomGroup() {
        return GROUPS[random.nextInt(GROUPS.length)];
    }
}
