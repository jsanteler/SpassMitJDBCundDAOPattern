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


    private int countStudentsInDbWithId(Long id) {

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
                Student student = new Student(
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
        String sql = "SELECT * FROM `Student`";
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


    @Override
    public Optional<Student> update(Student entity) {
        Assert.notNull(entity);

        String sql = "UPDATE `Student` SET `vorname` = ?, `nachname` = ?, `geburtsdatum` = ? WHERE `Student`.`id` = ?";

        if (countStudentsInDbWithId(entity.getId()) == 0) {
            return Optional.empty();
        } else {
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, entity.getVorname());
                preparedStatement.setString(2, entity.getNachname());
                preparedStatement.setDate(3, entity.getGeburtstag());
                preparedStatement.setLong(4, entity.getId());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    return Optional.empty();
                } else {
                    return this.getByID(entity.getId());
                }
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id);
        String sql = "DELETE FROM `Student` WHERE `id` = ?";
        try {
            if (countStudentsInDbWithId(id) == 1) {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Student> findStudentByName(String name) {

        Assert.notNull(name);
        try {
            String sql = "SELECT * FROM `Student` WHERE LOWER(`vorname`) LIKE LOWER (?) OR LOWER(`nachname`) LIKE LOWER (?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                studentList.add(new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("vorname"),
                        resultSet.getString("nachname"),
                        resultSet.getDate("geburtsdatum")
                ));
            }
            return studentList;

        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }



    @Override
    public List<Student> findStudentById(Long id) {
        Assert.notNull(id);

        try {
            String sql = "SELECT * FROM `Student` WHERE LOWER(`id`) = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                studentList.add(new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("vorname"),
                        resultSet.getString("nachname"),
                        resultSet.getDate("geburtsdatum")
                ));

            }
            return studentList;
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }


    @Override
    public List<Student> findStudentbyBirthdate(Date geburtstag) {

        Assert.notNull(geburtstag);
        String sql = "SELECT * FROM `Student` WHERE `geburtsdatum` = ? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDate(1, geburtstag);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                studentList.add(new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("vorname"),
                        resultSet.getString("nachname"),
                        resultSet.getDate("geburtsdatum")
                ));
            }
            return studentList;
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }
}




