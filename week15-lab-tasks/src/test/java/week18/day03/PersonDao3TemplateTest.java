package week18.day03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonDao3TemplateTest {

    PersonDao3Template personDao3Template;

    @BeforeEach
    void init() throws SQLException {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        dataSource.setUrl("jdbc:mariadb://localhost:3306/people?useUnicode=true");
        dataSource.setUser("peopleuser");
        dataSource.setPassword("peoplepassword");
        personDao3Template = new PersonDao3Template(dataSource);

//        deleteAndCreateTable(dataSource);
    }

    @Test
    void testSavePerson(){
        Person person = new Person("Németh Nóra", LocalDate.of(1989, 06, 15), 163, 62.3);
        personDao3Template.savePerson(person);
        Person expected = personDao3Template.findById(1);

        assertEquals("Németh Nóra", expected.getNameOfPerson());
    }

    private void deleteAndCreateTable(DataSource dataSource) throws SQLException {
        try(Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()){
            stmt.executeUpdate("drop table person");
            stmt.executeUpdate("CREATE TABLE if NOT EXISTS person (\n" +
                    "id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "name_of_person VARCHAR(250),\n" +
                    "date_of_birth DATE,\n" +
                    "height INT,\n" +
                    "weight FLOAT,\n" +
                    "PRIMARY KEY (id)\n" +
                    ");");
        }
    }

}