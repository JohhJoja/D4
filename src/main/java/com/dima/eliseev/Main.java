package com.dima.eliseev;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        final String URL = "jdbc:postgresql://localhost:5433/JDBC";
        final String USER = "postgres";
        final String PASSWORD = "1";

        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()){
            statement.executeUpdate("drop table Dimasik");
            statement.executeUpdate("create table Dimasik (id bigserial primary key, name varchar(50), age int)");
            statement.executeUpdate("insert into Dimasik (name, age) values ('Dimasik', 21)");
            ResultSet resultSet = statement.executeQuery("select * from Dimasik");
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
