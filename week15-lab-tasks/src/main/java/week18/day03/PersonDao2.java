package week18.day03;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PersonDao2 {

    private MariaDbDataSource dataSource;

    public PersonDao2(MariaDbDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        PersonDao2 personDao2;
        try {
            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/people?useUnicode=true");
            dataSource.setUser("peopleuser");
            dataSource.setPassword("peoplepassword");
            personDao2 = new PersonDao2(dataSource);
        } catch (SQLException sqle) {
            throw new IllegalStateException("Can not reach database.", sqle);
        }

//        try(Connection conn = dataSource.getConnection();
//            Statement stmt = conn.createStatement()){
//            stmt.executeUpdate("insert into person (name_of_person, date_of_birth, height, weight) values ('Kiss József', '1985-02-03',187,67.4);");
//            stmt.executeUpdate("insert into person (name_of_person, date_of_birth, height, weight) values ('Nagy Béla', '1992-12-23',172,78.2);");
//            stmt.executeUpdate("insert into person (name_of_person, date_of_birth, height, weight) values ('Németh Nóra', '1989-06-15',163,62.3);");
//        }catch (SQLException sqle){
//            throw new IllegalStateException("Can not insert.", sqle);
//        }

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
//        personDao2.savePerson(new Person("Kiss József", LocalDate.of(1985, 02, 03), 187, 67.4));
        List<Person> people = Arrays.asList(
                new Person("Kiss József", LocalDate.of(1985, 02, 03), 187, 67.4),
                new Person("Nagy Béla", LocalDate.of(1992, 12, 23), 172, 78.2),
                new Person("Németh Nóra", LocalDate.of(1989, 06, 15), 163, 62.3));
        personDao2.savePersons(people);
    }

    public void savePerson(Person person) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("insert into person (name_of_person, date_of_birth, height, weight) values (?,?,?,?);")) {
            stmt.setString(1, person.getNameOfPerson());
            stmt.setDate(2, Date.valueOf(person.getDateOfBirth()));
            stmt.setInt(3, person.getHeight());
            stmt.setDouble(4, person.getWeight());
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new IllegalStateException("Can not insert.", sqle);
        }
    }

    public void savePersons(List<Person> people) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("insert into person (name_of_person, date_of_birth, height, weight) values (?,?,?,?);")) {
            for (Person person : people) {
                stmt.setString(1, person.getNameOfPerson());
                stmt.setDate(2, Date.valueOf(person.getDateOfBirth()));
                stmt.setInt(3, person.getHeight());
                stmt.setDouble(4, person.getWeight());
                stmt.executeUpdate();
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Can not insert.", sqle);
        }
    }
}
