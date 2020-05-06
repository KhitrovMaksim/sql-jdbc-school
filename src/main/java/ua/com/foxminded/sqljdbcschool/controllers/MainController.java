package ua.com.foxminded.sqljdbcschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.com.foxminded.sqljdbcschool.repo.CoursesRepository;
import ua.com.foxminded.sqljdbcschool.repo.GroupsRepository;
import ua.com.foxminded.sqljdbcschool.repo.StudentsRepository;

@Controller
public class MainController {

    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private GroupsRepository groupsRepository;

    @GetMapping("/")
    public String home(Model model) {
        long studentsCount = studentsRepository.count();
        long coursesCount = coursesRepository.count();
        long groupsCount = groupsRepository.count();
        
        model.addAttribute("studentsCount", studentsCount);
        model.addAttribute("coursesCount", coursesCount);
        model.addAttribute("groupsCount", groupsCount);
        model.addAttribute("title", "Welcome to our School");
        
        return "home";
    }
}
