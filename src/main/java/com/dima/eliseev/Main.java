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

//        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
//            Statement statement = connection.createStatement()){
//           // statement.executeUpdate("create table blobi (id bigserial primary key, name varchar(50), img bytea)");
//            BufferedImage img = ImageIO.read(new File("E:\\Java\\deeplomka\\JDBC\\src\\main\\resources\\img.jpg"));
//            ByteArrayOutputStream BOAS = new ByteArrayOutputStream();
//            ImageIO.write(img, "jpg", BOAS);
//            byte[] bytedImg = BOAS.toByteArray();
//
//            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into blobi (name, img) values (?,?)");
//            preparedStatement1.setString(1,"Uranus");
//            preparedStatement1.setBytes(2, bytedImg);
//           // preparedStatement.execute();
//            PreparedStatement preparedStatement2 = connection.prepareStatement("select img from blobi where id = 2");
//            ResultSet resultSet = preparedStatement2.executeQuery();
//            resultSet.next();
//            byte[] r_byteImg = resultSet.getBytes("img");
//            File OF = new File("E:\\Java\\deeplomka\\JDBC\\src\\main\\resources\\saved.jpg");
//            ByteArrayInputStream bais = new ByteArrayInputStream(r_byteImg);
//            BufferedImage retrievedImage = ImageIO.read(bais);
//            ImageIO.write(retrievedImage, "jpg", OF);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD); Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery("");

        }
    }
}
