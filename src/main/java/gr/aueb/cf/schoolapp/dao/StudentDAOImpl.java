package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Student;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class StudentDAOImpl extends AbstractDAO<Student> implements IStudentDAO {

    public StudentDAOImpl() {
        this.setPersistenceClass(Student.class);
    }

}