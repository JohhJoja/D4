package com.dima.eliseev;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        String URL = "jdbc:postgresql://localhost:5433/JDBC";
        String NAME = "postgres";
        String PASSWORD = "1";

        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
             Statement statement = connection.createStatement()){

            Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            connection.setAutoCommit(false);
            statement1.executeUpdate("INSERT INTO dimasik (name, age) values ('Dimass', 39)");
            statement1.executeUpdate("INSERT INTO dimasik (name, age) values ('Anananananaaaa', 9)");
            statement1.executeUpdate("INSERT INTO dimasik (name, age) values ('Chuchpan', 3)");

            connection.commit();

        }




    catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
