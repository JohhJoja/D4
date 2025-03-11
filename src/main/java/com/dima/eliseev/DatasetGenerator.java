package com.dima.eliseev;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DatasetGenerator {

    private static final String[] STATUS_VALUES = {"открыт", "закрыт", "ожидает"};
    private static final String[] GROUPS = {"А", "Б", "В", "Г", "Д"};
    private static final String[] BEGINNING = {"Найди, пожалуйста, ", "Прошу предоставить информацию ", " скинь мне ", "Достань инфу об ", " найди заявку "};
    private static final Random random = new Random();

    public static void main(String[] args) {
        generateDataset("dataset.json", 1000);
    }

    public static void generateDataset(String filename, int count) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode dataset = mapper.createArrayNode();

        for (int i = 0; i < count; i++) {
            String code = "IM" + (100000 + random.nextInt(900000));
            String status = getRandomStatus();
            String group = getRandomGroup();
            String beginning = getRandomBeginning();
            String lastName = getRandomLastName();
            String firstName = getRandomFirstName();
            String middleName = getRandomMiddleName();

            // Включаем имя, фамилию и отчество в запрос
            String textQuery = String.format("%s заявку с кодом %s и статусом %s в группе %s для пользователя %s %s %s",
                    beginning, code, status, group, firstName, middleName, lastName);

            String sqlQuery = String.format("SELECT * FROM tickets WHERE code = '%s' AND status = '%s' AND groupS = '%s' AND last_name = '%s' AND first_name = '%s' AND middle_name = '%s'",
                    code, status, group, lastName, firstName, middleName);

            ObjectNode entry = mapper.createObjectNode();
            entry.put("text", textQuery);
            entry.put("sql", sqlQuery);
            dataset.add(entry);
        }

        try {
            mapper.writeValue(new File(filename), dataset);
            System.out.println("Датасет успешно создан в формате JSON!");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private static String getRandomLastName() {
        return DB_data_generator.LAST_NAMES[random.nextInt(DB_data_generator.LAST_NAMES.length)];
    }

    private static String getRandomFirstName() {
        return DB_data_generator.FIRST_NAMES[random.nextInt(DB_data_generator.FIRST_NAMES.length)];
    }

    private static String getRandomMiddleName() {
        return DB_data_generator.MIDDLE_NAMES[random.nextInt(DB_data_generator.MIDDLE_NAMES.length)];
    }
}
