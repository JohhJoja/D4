package com.dima.eliseev;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        String URL = "jdbc:postgresql://localhost:5433/JDBC";
        String NAME = "postgres";
        String PASSWORD = "1";

        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            Statement statement = connection.createStatement()){
           // statement.executeUpdate("create table blobi (id bigserial primary key, name varchar(50), img bytea)");

            BufferedImage img = ImageIO.read(new File("E:\\Java\\deeplomka\\JDBC\\src\\main\\resources\\img.jpg"));

            ByteArrayOutputStream babun = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", babun);
            byte[] imageBytes = babun.toByteArray();

            PreparedStatement preparedStatement = connection.prepareStatement("insert into blobi (name, img) values (?,?)");
            preparedStatement.setString(1,"Dimasik");
            preparedStatement.setBytes(2,imageBytes);
            preparedStatement.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
