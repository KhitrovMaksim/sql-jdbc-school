package ua.com.foxminded.sqljdbcschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqlJdbcSchoolApplication {

    public static void main(String[] args) {
        SchoolData schoolData = new SchoolData();
        schoolData.generate();
        
        SpringApplication.run(SqlJdbcSchoolApplication.class, args);
    }
}
