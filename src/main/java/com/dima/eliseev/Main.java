package com.dima.eliseev;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        String URL = "jdbc:postgresql://localhost:5433/JDBC";
        String NAME = "postgres";
        String PASSWORD = "1";

        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            Statement statement = connection.createStatement()){
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Dimasik where NAME = ?");
            preparedStatement.setString(1, "Dimasik");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
            }
        }

        My_thread th1 = new My_thread();
        th1.start();

    }
}
