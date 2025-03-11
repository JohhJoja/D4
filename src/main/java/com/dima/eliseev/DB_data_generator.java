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
    static final String[] LAST_NAMES = {
            "Александров", "Алексеев", "Анисимов", "Антонов", "Архипов", "Афанасьев", "Беляев", "Борисов", "Быков", "Васильев",
            "Веселов", "Виноградов", "Вишняков", "Волков", "Воробьев", "Воронин", "Гаврилов", "Горбачев", "Григорьев", "Громов",
            "Гусев", "Данилов", "Дементьев", "Денисов", "Дмитриев", "Дорохов", "Дроздов", "Евсеев", "Егоров", "Еремеев",
            "Ершов", "Жданов", "Жуков", "Зайцев", "Захаров", "Зимин", "Зиновьев", "Зуев", "Иванов", "Игнатов",
            "Ильин", "Исаков", "Казаков", "Калашников", "Карпов", "Кириллов", "Киселев", "Князев", "Козлов", "Колесников",
            "Комаров", "Кондратьев", "Коновалов", "Котов", "Кошелев", "Краев", "Крылов", "Кудрявцев", "Кузнецов", "Кулаков",
            "Лазарев", "Лапшин", "Лебедев", "Лихачев", "Лукин", "Макаров", "Мамонтов", "Марков", "Матвеев", "Медведев",
            "Мельников", "Миронов", "Михайлов", "Моисеев", "Морозов", "Муравьев", "Мухин", "Назаров", "Нестеров", "Никитин",
            "Николаев", "Овчинников", "Орехов", "Осипов", "Павлов", "Панов", "Петров", "Поляков", "Попов", "Рогов",
            "Родионов", "Романов", "Русаков", "Рябов", "Савельев", "Сафронов", "Седов", "Селиванов", "Семенов", "Сергеев"
    };
    static final String[] FIRST_NAMES = {"Александр", "Алексей", "Анатолий",
            "Андрей", "Антон", "Аркадий", "Артем", "Богдан", "Борис", "Валентин",
            "Валерий", "Василий", "Виктор", "Виталий", "Владислав", "Вячеслав",
            "Геннадий", "Георгий", "Глеб", "Григорий", "Даниил", "Денис", "Дмитрий",
            "Евгений", "Егор", "Ефим", "Захар", "Иван", "Игнат", "Игорь", "Илья",
            "Иннокентий", "Казимир", "Кирилл", "Константин", "Лев", "Леонид", "Макар",
            "Максим", "Марк", "Матвей", "Мирон", "Михаил", "Назар", "Никита", "Николай",
            "Олег", "Павел", "Петр", "Платон", "Прохор", "Родион", "Роман", "Ростислав",
            "Савелий", "Святослав", "Семен", "Сергей", "Станислав", "Степан", "Тарас",
            "Тимофей", "Тимур", "Федор", "Филипп", "Харитон", "Эдуард", "Юлиан", "Юрий",
            "Яков", "Ян", "Ярослав", "Арсен", "Арсений", "Альберт", "Альфред", "Герман",
            "Демид", "Ермолай", "Зиновий", "Карл", "Клим", "Лаврентий", "Мартиан",
            "Мечеслав", "Нестор", "Оскар", "Ратибор", "Святополк", "Севастьян",
            "Соломон", "Тихон", "Устин", "Феофан", "Хрисанф", "Эльдар",
            "Ювеналий", "Ярополк"
    };
    static final String[] MIDDLE_NAMES = {
            "Александрович", "Алексеевич", "Анатольевич", "Андреевич", "Антонович", "Аркадьевич", "Артемович", "Борисович", "Вадимович", "Валентинович",
            "Валериевич", "Васильевич", "Викторович", "Витальевич", "Владимирович", "Вячеславович", "Геннадьевич", "Георгиевич", "Григорьевич", "Данилович",
            "Дмитриевич", "Евгеньевич", "Егорович", "Ефимович", "Захарович", "Иванович", "Игоревич", "Ильич", "Кириллович", "Константинович",
            "Леонидович", "Макарович", "Максимович", "Матвеевич", "Михайлович", "Никитич", "Николаевич", "Олегович", "Павлович", "Петрович",
            "Романович", "Семенович", "Сергеевич", "Станиславович", "Степанович", "Тимофеевич", "Тихонович", "Федорович", "Филиппович", "Юрьевич",
            "Адамович", "Альбертович", "Арсеньевич", "Вениаминович", "Гаврилович", "Глебович", "Добрынович", "Денисович", "Зиновьевич", "Изотович",
            "Игнатьевич", "Кузьмич", "Лукьянович", "Мартынович", "Миронович", "Наумович", "Панкратьевич", "Прокофьевич", "Рубенович", "Святославович",
            "Саввич", "Сильвестрович", "Сократович", "Фролович", "Эдуардович", "Юлианович", "Яковлевич", "Аверьянович", "Авдеевич", "Богданович",
            "Вильевич", "Всеволодович", "Германович", "Давидович", "Елисеевич", "Ефремович", "Зигмундович", "Игнатович", "Иеронимович", "Капитонович",
            "Леонтьевич", "Моисеевич", "Несторович", "Онисимович", "Родионович", "Тарасович", "Устинович", "Харитонович", "Цезаревич", "Ярославович"
    };
    private static final String[] STATUS_VALUES = {"открыт", "закрыт", "ожидает"};
    private static final String[] GROUPS = {"А", "Б", "В", "Г", "Д"};

    private static final Random random = new Random();

    public static void GENERATE() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println(" Подключение к БД успешно!");

            String sql = "INSERT INTO tickets (name1, status, code, signetTo, time, urgency, groupS) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (int i = 0; i < 1000; i++) {
                    // Генерация случайных значений
                    String name1 = getRandomFIO();
                    String status = getRandomStatus();
                    String code = "IM" + (100000 + random.nextInt(900000));
                    String signetTo = getRandomFIO();

                    // Генерация случайной даты и времени
                    int year = random.nextInt(6) + 2020; // Случайный год от 2020 до 2025
                    int month = random.nextInt(12) + 1; // Случайный месяц от 1 до 12
                    int day = random.nextInt(28) + 1; // Случайный день, ограничим 28 днями для простоты (чтобы избежать переполнения месяцев)
                    int hour = random.nextInt(24); // Случайный час от 0 до 23
                    int minute = random.nextInt(60); // Случайная минута от 0 до 59
                    int second = random.nextInt(60); // Случайная секунда от 0 до 59

                    String dateStr = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
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
                        System.out.println(" Добавлено " + (i + 1) + " записей...");
                    }
                }
                statement.executeBatch(); // Отправляем оставшиеся записи
                System.out.println(" Данные успешно добавлены!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("problem!");
        }
    }

    private static String getRandomFIO() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)] + " " +
                FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] + " " +
                MIDDLE_NAMES[random.nextInt(MIDDLE_NAMES.length)];
    }

    private static String getRandomStatus() {return STATUS_VALUES[random.nextInt(STATUS_VALUES.length)];}

    private static String getRandomGroup() {
        return GROUPS[random.nextInt(GROUPS.length)];
    }
}
