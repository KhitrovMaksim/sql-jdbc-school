package ua.com.foxminded.sqljdbcschool.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ua.com.foxminded.sqljdbcschool.models.Courses;
import ua.com.foxminded.sqljdbcschool.models.Students;
import ua.com.foxminded.sqljdbcschool.repo.CoursesRepository;
import ua.com.foxminded.sqljdbcschool.repo.StudentsRepository;

@Controller
public class CoursesController {
    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @GetMapping("/courses")
    public String coursesMainPage(Model model) {
        model.addAttribute("title", "Courses");
        Iterable<Courses> courses = coursesRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses-main-page";
    }
    
    @GetMapping("/courses/{id}")
    public String courseStudents(@PathVariable(value = "id") int id, Model model) {
        Courses course = coursesRepository.findById(id).orElseThrow(null);
        model.addAttribute("title", "Courses" + course.getCourse_name());
        Set<Students> students = course.getStudents();
        int count = students.size();
        model.addAttribute("count", count);
        model.addAttribute("course", id);
        model.addAttribute("students", students);
        return "course-students";
    }
    
    @PostMapping("/courses/{courseId}/delete/{studentId}")
    public String courseDeleteStudents(@PathVariable(value = "courseId") int courseId, @PathVariable(value = "studentId") int studentId, Model model) {
        Courses course = coursesRepository.findById(courseId).orElseThrow(null);
        model.addAttribute("title", "Courses" + course.getCourse_name());
        Students student = studentsRepository.findById(studentId).orElseThrow(null);
        course.removeStudent(student);
        coursesRepository.save(course);
        Set<Students> students = course.getStudents();
        model.addAttribute("students", students);
        return "redirect:/courses/" + courseId;
    }
}
