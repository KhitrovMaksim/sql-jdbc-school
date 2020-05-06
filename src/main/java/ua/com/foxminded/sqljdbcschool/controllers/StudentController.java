package ua.com.foxminded.sqljdbcschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    @GetMapping("/students/add")
    public String studentAdd(Model model) {
        model.addAttribute("title", "Adding a new student");
        return "student-add";
    }
    
    @PostMapping("/students/add")
    public String studentAddPost(@RequestParam String first_name, @RequestParam String last_name, @RequestParam Integer group, Model model) {
        Students studens = new Students(first_name, last_name, group);
        studentsRepository.save(studens);
        return "redirect:/students";
    }
}
