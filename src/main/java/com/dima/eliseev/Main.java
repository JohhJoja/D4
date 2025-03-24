package com.dima.eliseev;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        String URL = "jdbc:postgresql://localhost:5433/JDBC";
        String NAME = "postgres";
        String PASSWORD = "1";

        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select * from Dimasik");
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
            }
        }

    }
}
