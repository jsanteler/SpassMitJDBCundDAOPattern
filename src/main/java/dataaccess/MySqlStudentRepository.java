package dataaccess;

import domain.Course;
import domain.CourseType;
import domain.Student;
import util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository {

    private Connection con;

    public MySqlStudentRepository() throws SQLException, ClassNotFoundException {

        this.con = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "1234");
    }

    public Optional<Student> insert(Student entity) {
        Assert.notNull(entity);

        try {
            String sql = "INSERT INTO `Student` (`id`, `vorname`, `nachname`, `geburtsdatum`) VALUES(NULL, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getVorname());
            preparedStatement.setString(2, entity.getNachname());
            preparedStatement.setDate(3, entity.getGeburtstag());

            int affectedRows = preparedStatement.executeUpdate();


            if (affectedRows == 0) {
                return Optional.empty();
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {

                return this.getByID(generatedKeys.getLong(1));
            } else {
                return Optional.empty();
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }


    private int countStudentsInDbWithId(Long id){

        try {
            String countSql = "SELECT COUNT(*) FROM `Student` WHERE `id`=?";
            PreparedStatement preparedStatementCount = con.prepareStatement(countSql);
            preparedStatementCount.setLong(1, id);
            ResultSet resultSetCount = preparedStatementCount.executeQuery();
            resultSetCount.next();
            int courseCount = resultSetCount.getInt(1);
            return courseCount;
        } catch (SQLException sqlException) {

            throw new DatabaseException(sqlException.getMessage());
        }
    }


    @Override
    public Optional<Student> getByID(Long id) {

        Assert.notNull(id);
        if (countStudentsInDbWithId(id) == 0) {
            return Optional.empty();
        } else {
            try {
                String sql = "SELECT * FROM `Student` WHERE `id` = ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.next();
                Student student= new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("vorname"),
                        resultSet.getString("nachname"),
                        resultSet.getDate("geburtsdatum")
                );
                return Optional.of(student);
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }
    }



    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM `Students`";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                studentList.add(new Student(
                                resultSet.getLong("id"),
                                resultSet.getString("vorname"),
                                resultSet.getString("nachname"),
                                resultSet.getDate("geburtsdatum")
                        )
                );
            }
            return studentList;
        } catch (SQLException e) {
            throw new DatabaseException("Database error occured!");
        }
    }

}

    @Override
    public Optional<Student> update(Student entity) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Student> findStudentByName(String name) {
        return null;
    }

    @Override
    public List<Student> findStudentById(Long id) {
        return null;
    }

    @Override
    public List<Student> findStudentbyBirthdate(Date geburtstag) {
        return null;
    }
}



