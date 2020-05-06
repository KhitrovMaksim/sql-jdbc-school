package ua.com.foxminded.sqljdbcschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.com.foxminded.sqljdbcschool.models.Courses;
import ua.com.foxminded.sqljdbcschool.repo.CoursesRepository;

@Controller
public class CoursesController {
    @Autowired
    private CoursesRepository coursesRepository;

    @GetMapping("/courses")
    public String coursesMainPage(Model model) {
        model.addAttribute("title", "Courses");
        Iterable<Courses> courses = coursesRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses-main-page";
    }
}
