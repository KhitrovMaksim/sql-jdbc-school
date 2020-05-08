package ua.com.foxminded.sqljdbcschool.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/students/{id}/edit")
    public String studentEdit(@PathVariable(value = "id") int id, Model model) {
        if(!studentsRepository.existsById(id)) {
            return "redirect:/students";
        }
        model.addAttribute("title", "Student profile editing");
        Optional<Students> studens = studentsRepository.findById(id);
        List<Students> result = new ArrayList<>();
        studens.ifPresent(result::add);
        model.addAttribute("student", result);
        return "student-edit";
    }
}
