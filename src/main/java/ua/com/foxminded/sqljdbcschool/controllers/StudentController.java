package ua.com.foxminded.sqljdbcschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.com.foxminded.sqljdbcschool.models.Students;
import ua.com.foxminded.sqljdbcschool.repo.StudentsRepository;

@Controller
public class StudentController {
    
    @Autowired
    private StudentsRepository studentsRepository;
    
    @GetMapping("/students")
    public String studentsMainPage(Model model) {
        model.addAttribute("title", "Students");
        Iterable<Students> students = studentsRepository.findAll();
        model.addAttribute("students", students);
        return "students-main-page";
    }
}
