package com.dima.eliseev;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

public class DatasetGenerator {
    private static final String[] LAST_NAMES = {"Александров", "Алексеев", "Анисимов", "Антонов", "Архипов"};
    private static final String[] FIRST_NAMES = {"Александр", "Алексей", "Анатолий", "Андрей", "Антон"};
    private static final String[] MIDDLE_NAMES = {"Александрович", "Алексеевич", "Анатольевич", "Андреевич", "Антонович"};
    private static final String[] STATUS_VALUES = {"открыт", "закрыт", "ожидает"};
    private static final String[] GROUPS = {"А", "Б", "В", "Г", "Д"};
    private static final String[] BEGINNING = {"Найди, пожалуйста, ","Прошу предоставить информацию "," скинь мне ","Достань инфу об "," найди заявку "};
    private static final Random random = new Random();

    public static void main(String[] args) {
        generateDataset("dataset.txt", 1000);
    }

    public static void generateDataset(String filename, int count) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < count; i++) {
                String name1 = getRandomFIO();
                String status = getRandomStatus();
                String code = "IM" + (100000 + random.nextInt(900000));
                String beginning = getRandomBeginning();
                String signetTo = getRandomFIO();
                Timestamp timestamp = getRandomTimestamp();
                int urgency = random.nextInt(5) + 1;
                String group = getRandomGroup();

                String textQuery = String.format("%s заявку с кодом %s и статусом %s в группе %s", beginning, code, status, group);
                String sqlQuery = String.format("SELECT * FROM tickets WHERE code = '%s' AND status = '%s' AND groupS = '%s'", code, status, group);

                writer.write(textQuery + "\t" + sqlQuery + "\n");
            }
            System.out.println("Датасет успешно создан!");
        } catch (IOException e) {
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

    private static String getRandomBeginning() {
        return BEGINNING[random.nextInt(BEGINNING.length)];
    }

    private static Timestamp getRandomTimestamp() {
        int year = random.nextInt(6) + 2020;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        String dateStr = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
        return Timestamp.valueOf(dateStr);
    }
}
