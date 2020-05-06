package ua.com.foxminded.sqljdbcschool.repo;

import org.springframework.data.repository.CrudRepository;
import ua.com.foxminded.sqljdbcschool.models.Students;

public interface StudentsRepository extends CrudRepository<Students, Integer> {

}
