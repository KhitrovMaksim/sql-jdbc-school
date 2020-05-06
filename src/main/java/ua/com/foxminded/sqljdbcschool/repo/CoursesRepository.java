package ua.com.foxminded.sqljdbcschool.repo;

import org.springframework.data.repository.CrudRepository;
import ua.com.foxminded.sqljdbcschool.models.Courses;

public interface CoursesRepository extends CrudRepository<Courses, Integer> {

}
