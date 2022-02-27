package week18.day03;

import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PersonDao3Template {

    private MariaDbDataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public PersonDao3Template(MariaDbDataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static void main(String[] args) {
        PersonDao3Template personDao2;
        try {
            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/people?useUnicode=true");
            dataSource.setUser("peopleuser");
            dataSource.setPassword("peoplepassword");
            personDao2 = new PersonDao3Template(dataSource);
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
//        List<Person> people = Arrays.asList(
//                new Person("Kiss József", LocalDate.of(1985, 02, 03), 187, 67.4),
//                new Person("Nagy Béla", LocalDate.of(1992, 12, 23), 172, 78.2),
//                new Person("Németh Nóra", LocalDate.of(1989, 06, 15), 163, 62.3));
//        personDao2.savePersons(people);

//        personDao2.updateName(10,"xy");
//        personDao2.updateName(11,"xy");
//        personDao2.deletePerson(12);
//        personDao2.deletePerson(13);
//        Person person = personDao2.findById(8);
//        System.out.println(person.getNameOfPerson());

//        System.out.println(personDao2.listPeopleNames());

//        personDao2.savePersonAndGetGeneratedKey(new Person("Németh Nóra", LocalDate.of(1989, 06, 15), 163, 62.3));
    }

    public void savePerson(Person person) {
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement stmt = conn.prepareStatement("insert into person (name_of_person, date_of_birth, height, weight) values (?,?,?,?);")) {
//            stmt.setString(1, person.getNameOfPerson());
//            stmt.setDate(2, Date.valueOf(person.getDateOfBirth()));
//            stmt.setInt(3, person.getHeight());
//            stmt.setDouble(4, person.getWeight());
//            stmt.executeUpdate();
//        } catch (SQLException sqle) {
//            throw new IllegalStateException("Can not insert.", sqle);
//        }

        jdbcTemplate.update("insert into person (name_of_person, date_of_birth, height, weight) values (?,?,?,?);",
                person.getNameOfPerson(), person.getDateOfBirth(), person.getHeight(), person.getWeight());
    }

    public void savePersons(List<Person> people) {
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement stmt = conn.prepareStatement("insert into person (name_of_person, date_of_birth, height, weight) values (?,?,?,?);")) {
//            for (Person person : people) {
//                stmt.setString(1, person.getNameOfPerson());
//                stmt.setDate(2, Date.valueOf(person.getDateOfBirth()));
//                stmt.setInt(3, person.getHeight());
//                stmt.setDouble(4, person.getWeight());
//                stmt.executeUpdate();
//            }
//        } catch (SQLException sqle) {
//            throw new IllegalStateException("Can not insert.", sqle);
//        }
        for (Person person : people) {
            jdbcTemplate.update("insert into person (name_of_person, date_of_birth, height, weight) values (?,?,?,?);",
                    person.getNameOfPerson(), person.getDateOfBirth(), person.getHeight(), person.getWeight());
        }
    }

    public void updateName(long id, String newName) {
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement stmt = conn.prepareStatement("update person set name_of_person = ? where id = ?;")) {
//            stmt.setString(1, newName);
//            stmt.setLong(2, id);
//            stmt.executeUpdate();
//        } catch (SQLException sqlException) {
//            throw new IllegalStateException("Cannot update", sqlException);
//        }

        jdbcTemplate.update("update person set name_of_person = ? where id = ?;", newName, id);
    }

    public void deletePerson(long id) {
//        try(Connection conn = dataSource.getConnection();
//        PreparedStatement stmt = conn.prepareStatement("delete from person where id = ?;")){
//            stmt.setLong(1,id);
//            stmt.executeUpdate();
//        }catch (SQLException sqlException){
//            throw new IllegalStateException("Cannot find id", sqlException);
//        }

        jdbcTemplate.update("delete from person where id = ?;", id);
    }

    public Person findById(long id) {
//        try(Connection conn = dataSource.getConnection();
//        PreparedStatement stmt = conn.prepareStatement("select * from person where id = ?;")){
//            stmt.setLong(1,id);
//            try(ResultSet rs = stmt.executeQuery()){
//                if(rs.next()){
//                    String name = rs.getString("name_of_person");
//                    LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
//                    int height = rs.getInt("height");
//                    double weight = rs.getDouble("weight");
//                    return new Person(name,dateOfBirth,height,weight);
//                }
//            }
//        }catch (SQLException sqlException){
//            throw new IllegalStateException("Cannot find person", sqlException);
//        }
//        throw new IllegalArgumentException("No such person");

        return jdbcTemplate.queryForObject(
                "select * from person where id = ?;",
                (rs, rowNum) -> new Person(
                        rs.getString("name_of_person"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getInt("height"),
                        rs.getDouble("weight")),
                id);
    }

    public Optional<List<String>> listPeopleNames() {
//        try(Connection conn = dataSource.getConnection();
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("select name_of_person from person;")){
//            List<String> result = new ArrayList<>();
//            while (rs.next()){
//                result.add(rs.getString("name_of_person"));
//            }
//            return Optional.of(result);
//        }catch (SQLException sqlException){
//            throw new IllegalStateException("Cannot find people", sqlException);
//        }

        return Optional.of(jdbcTemplate.query("select name_of_person from person;",
                (rs, rowNum) -> rs.getString("name_of_person")));
    }

    public void savePersonAndGetGeneratedKey(Person person) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "insert into person (name_of_person, date_of_birth, height, weight) values (?,?,?,?);",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, person.getNameOfPerson());
            stmt.setDate(2, Date.valueOf(person.getDateOfBirth()));
            stmt.setInt(3, person.getHeight());
            stmt.setDouble(4, person.getWeight());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys();) {
                if (rs.next()) {
                    System.out.println(rs.getLong(1));
                }
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Can not insert.", sqle);
        }
    }

    public void updateNameInTransaction(long id, String newName) {
        try (Connection conn = dataSource.getConnection()){
            conn.setAutoCommit(false);
            try(PreparedStatement stmt = conn.prepareStatement("update person set name_of_person = ? where id = ?;")) {
                stmt.setString(1, newName);
                stmt.setLong(2, id);
                stmt.executeUpdate();
                conn.commit();
            }catch (Exception e){
                conn.rollback();
                throw new IllegalStateException("Transaction not succeeded",e);
            }
        }catch (SQLException sqlException) {
            throw new IllegalStateException("Cannot update", sqlException);
        }

//        jdbcTemplate.update("update person set name_of_person = ? where id = ?;", newName, id);
    }

    @Transactional
    public void updateNameInTransactionWithTemplate(long id, String newName) {
//        try (Connection conn = dataSource.getConnection()){
//            conn.setAutoCommit(false);
//            try(PreparedStatement stmt = conn.prepareStatement("update person set name_of_person = ? where id = ?;")) {
//                stmt.setString(1, newName);
//                stmt.setLong(2, id);
//                stmt.executeUpdate();
//                conn.commit();
//            }catch (Exception e){
//                conn.rollback();
//                throw new IllegalStateException("Transaction not succeeded",e);
//            }
//        }catch (SQLException sqlException) {
//            throw new IllegalStateException("Cannot update", sqlException);
//        }

        jdbcTemplate.update("update person set name_of_person = ? where id = ?;", newName, id);
    }
}
