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

            Statement stat = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = stat.executeQuery("Select * from blobi");
            if (resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
            if (resultSet.previous()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
        }
//             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM test1()")) {
//
//            boolean hasNext = preparedStatement.execute();
//            ResultSet resultSet = preparedStatement.getResultSet();
//            while (hasNext){
//                while (resultSet.next()) {
//                    System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
//                }
//                hasNext = preparedStatement.getMoreResults();
//            }



    catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
