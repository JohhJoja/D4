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

        REGEX.match_("a", "aaaaaaa");
        REGEX.match_("\\Q1+1=2\\E", "1+1=2 экранирует");
        REGEX.match_("1\\+1=2", "1+1=2 это тоже");

        REGEX.match_("[a-d]", "any letters from a to d");
        REGEX.match_("\\D", "qwesad1sd5asd4"); ///^d

        REGEX.match_("[0-9]", "F1nd any n4m5r1c");
        REGEX.match_("\\d", "qwesad1sd5asd4");

        REGEX.match_("[0-3a-b]","Any 75tt5r5 (a-b) and n4mb5rs (0-3)");
        REGEX.match_("[\\w]","Any1 simbl1");

        REGEX.match_("[\\s]","Find every space");
        REGEX.match_("[\\S]","Find all except space");

        REGEX.match_("[A-U]", "Find Any Uppercase Letters");
        REGEX.match_("[a-z}%@=]", "Find lett " +
                "+ this ch in any position (for example: Alola = @100%");
        REGEX.match_("gr[ae]y","Can find: gray or grey");
        REGEX.match_("q[^mmnbvcxzlkjhgfdsapoiuytre]", "qw");
        /// ^ - нужно указывать в начале, иначе, если будет в другом месте, то будет
        /// считаться обычным символом

        String text =
                "### **Тайна заброшенного особняка**  \n" +
                "\n" +
                "В маленьком городке, затерянном среди густых лесов, стоял старый особняк, окутанный тайнами и легендами. Жители с детства знали: туда лучше не заходить. Говорили, что в лунные ночи там слышны шорохи, а окна сами собой открываются и закрываются.  \n" +
                "\n" +
                "Однажды трое друзей – Артём, Дмитрий и Ксения – решили проверить, что скрывается за мрачными стенами. Осенью, когда листва кружилась в воздухе, а ветер выл в старых ветках, они отправились к особняку.  \n" +
                "\n" +
                "Дверь оказалась приоткрыта, словно кто-то ждал гостей. Внутри пахло сыростью, а пол скрипел под ногами. В свете фонариков мелькали силуэты старинной мебели, покрытой пылью и паутиной.  \n" +
                "\n" +
                "— Ты уверен, что это хорошая идея? – прошептала Ксения, сжимая руку Артёма.  \n" +
                "\n" +
                "— Конечно! Это просто старый дом, – ответил он, стараясь скрыть волнение.  \n" +
                "\n" +
                "Они углубились внутрь. Вдруг дверь за ними захлопнулась с громким стуком. Ребята вскрикнули. Свет фонариков дрожал в их руках.  \n" +
                "\n" +
                "— Это ветер! Просто ветер! – пытался успокоить всех Дмитрий.  \n" +
                "\n" +
                "Но тут на втором этаже раздались шаги. Кто-то или что-то двигалось по коридору. Друзья замерли.  \n" +
                "\n" +
                "— Кто здесь? – крикнул Артём.  \n" +
                "\n" +
                "Ответом была тишина. Затем слабый стук... раз... два... три...  \n" +
                "\n" +
                "Ксения сжала зубы:  \n" +
                "\n" +
                "— Уходим отсюда!  \n" +
                "\n" +
                "Они бросились к выходу, но дверь не поддавалась. Внезапно за спиной раздался шёпот:  \n" +
                "\n" +
                "— Не уходите...  \n" +
                "\n" +
                "Друзья обернулись, но никого не увидели.  \n" +
                "\n" +
                "Когда на следующее утро жители деревни пришли к особняку, внутри не было ни одной живой души. Только три рюкзака и едва различимые следы на пыльном полу...  \n" +
                "\n" +
                "---\n" +
                "\n" +
                "\uD83D\uDD39 **Нужен другой стиль? Например, научный текст, описание природы или что-то ещё?** \uD83D\uDE0F";
        Pattern pattern = Pattern.compile("\\s(т...т)\\W");
        Matcher m = pattern.matcher(text);
        while (m.find()){
            System.out.println(m.group(1));
        }
    }
}

class REGEX{
    static public void match_(String regex, String ex){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ex);
//        while (m.find()){
//            System.out.print(m.start() + " " + m.group() + " ");
//        }
//        System.out.println("---------------  "+ex);
    }
}

