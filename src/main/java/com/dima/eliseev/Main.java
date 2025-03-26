package com.dima.eliseev;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws SQLException {

        String URL = "jdbc:postgresql://localhost:5433/JDBC";
        String NAME = "postgres";
        String PASSWORD = "1";

//        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
//             Statement statement = connection.createStatement()){
//
//            Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//
//            connection.setAutoCommit(false);
//            statement1.executeUpdate("INSERT INTO dimasik (name, age) values ('Dimass', 39)");
//            Savepoint savepoint = connection.setSavepoint();
//            statement1.executeUpdate("INSERT INTO dimasik (name, age) values ('Anananananaaaa', 9)");
//            statement1.executeUpdate("INSERT INTO dimasik (name, age) values ('Chuchpan', 3)");
//
//            connection.rollback(savepoint); //откат изменений
//            connection.commit();
//
//        }
//    catch (SQLException e) {
//            e.printStackTrace();
//        }
//

        REGEX.match_A("My first regular expression aaaaa!");

    }
}

class REGEX{
    static public void match_A(String ex){
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher(ex);
        while (m.find()){
            System.out.println(m.start() + " " + m.group() + " ");
        }
    }
}