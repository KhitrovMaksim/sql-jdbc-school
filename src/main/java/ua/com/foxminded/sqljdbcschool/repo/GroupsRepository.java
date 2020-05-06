package ua.com.foxminded.sqljdbcschool.repo;

import org.springframework.data.repository.CrudRepository;
import ua.com.foxminded.sqljdbcschool.models.Groups;

public interface GroupsRepository extends CrudRepository<Groups, Integer> {

}
