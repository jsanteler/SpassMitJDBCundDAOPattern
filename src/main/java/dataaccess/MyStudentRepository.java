package dataaccess;

import domain.Student;

import java.sql.Date;
import java.util.List;

public interface MyStudentRepository extends BaseRepository<Student,Long>{

    List<Student> findStudentByName(String name);

    List<Student> findStudentById(Long id);

    List<Student> findStudentbyBirthdate(Date geburtstag);

}
