package week18.day03;

import day01.Main;
import org.mariadb.jdbc.MariaDbDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PersonDao {

    public static void main(String[] args) {
        MariaDbDataSource dataSource;
        try {
            dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/people?useUnicode=true");
            dataSource.setUser("peopleuser");
            dataSource.setPassword("peoplepassword");
        }
        catch (SQLException sqle){
            throw new IllegalStateException("Can not reach database.", sqle);
        }

        try(Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()){
            stmt.executeUpdate("insert into person (name_of_person, date_of_birth, height, weight) values ('Kiss József', '1985-02-03',187,67.4);");
            stmt.executeUpdate("insert into person (name_of_person, date_of_birth, height, weight) values ('Nagy Béla', '1992-12-23',172,78.2);");
            stmt.executeUpdate("insert into person (name_of_person, date_of_birth, height, weight) values ('Németh Nóra', '1989-06-15',163,62.3);");
        }catch (SQLException sqle){
            throw new IllegalStateException("Can not insert.", sqle);
        }

//        Properties prop = new Properties();
//        try(BufferedReader br = new BufferedReader(new InputStreamReader(PersonDao.class.getResourceAsStream("/people.properties")))){
//            prop.load(br);
////            System.out.println(prop.getProperty("url"));
////            System.out.println(prop.getProperty("user"));
////            System.out.println(prop.getProperty("password"));
//            MariaDbDataSource dataSource = new MariaDbDataSource();
//            dataSource.setUrl(prop.getProperty("url"));
//        }catch (IOException | SQLException ex){
//            throw new IllegalStateException("Cannot reach file", ex);
//        }
    }

    public void savePerson(Person person){

    }
}
