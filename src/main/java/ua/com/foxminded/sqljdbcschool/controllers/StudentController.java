package ua.com.foxminded.sqljdbcschool.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.sqljdbcschool.models.Courses;
import ua.com.foxminded.sqljdbcschool.models.Students;
import ua.com.foxminded.sqljdbcschool.repo.CoursesRepository;
import ua.com.foxminded.sqljdbcschool.repo.StudentsRepository;

@Controller
public class StudentController {
    @Autowired
    private StudentsRepository studentsRepository;
    
    @Autowired
    private CoursesRepository coursesRepository;

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
    public String studentAddPost(@RequestParam String first_name, @RequestParam String last_name,
            @RequestParam Integer group, Model model) {
        Students studens = new Students(first_name, last_name, group);
        studentsRepository.save(studens);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/edit")
    public String studentEdit(@PathVariable(value = "id") int id, Model model) {
        if (!studentsRepository.existsById(id)) {
            return "redirect:/students";
        }
        model.addAttribute("title", "Student profile editing");
        Optional<Students> studens = studentsRepository.findById(id);
        List<Students> result = new ArrayList<>();
        studens.ifPresent(result::add);
        model.addAttribute("student", result);
        return "student-edit";
    }

    @PostMapping("/students/{id}/edit")
    public String studentEditPost(@PathVariable(value = "id") int id, @RequestParam String first_name,
            @RequestParam String last_name, @RequestParam Integer group, Model model) {
        Students student = studentsRepository.findById(id).orElseThrow(null);
        student.setFirst_name(first_name);
        student.setLast_name(last_name);
        student.setGroup_id(group);
        studentsRepository.save(student);
        return "redirect:/students";
    }

    @PostMapping("/students/{id}/delete")
    public String studentDeletePost(@PathVariable(value = "id") int id, Model model) {
        Students student = studentsRepository.findById(id).orElseThrow(null);
        studentsRepository.delete(student);
        return "redirect:/students";
    }
    
    @GetMapping("/students/{id}/courses")
    public String studentCourses(@PathVariable(value = "id") int id, Model model) {
        Students student = studentsRepository.findById(id).orElseThrow(null);
        Iterable<Courses> allCourses = coursesRepository.findAll();
        Set<Courses> studentCourses = student.getCourses();
        Set<Courses> coursesToAdd = new HashSet<>();
        allCourses.forEach(coursesToAdd::add);
        coursesToAdd.removeAll(studentCourses);
        model.addAttribute("title", "Student: "  + student.getFirst_name() + " " + student.getLast_name());
        model.addAttribute("coursesToAdd", coursesToAdd);
        model.addAttribute("student", id);
        model.addAttribute("courses", studentCourses);
        return "student-courses";
    }
    
    @PostMapping("/students/{studentId}/courses/{courseId}/delete")
    public String studentDeleteCourses(@PathVariable(value = "studentId") int studentId, @PathVariable(value = "courseId") int courseId, Model model) {
        Students student = studentsRepository.findById(studentId).orElseThrow(null);
        model.addAttribute("title", "Student: "  + student.getFirst_name() + " " + student.getLast_name());
        Courses course = coursesRepository.findById(courseId).orElseThrow(null);
        student.removeCourse(course);
        studentsRepository.save(student);
        Set<Courses> courses = student.getCourses();
        model.addAttribute("courses", courses);
        return "redirect:/students/" + studentId + "/courses";
    }
    
    @PostMapping("/students/{studentId}/courses/add")
    public String studentAddCourses(@PathVariable(value = "studentId") int studentId, @RequestParam int courseId, Model model) {
        Students student = studentsRepository.findById(studentId).orElseThrow(null);
        model.addAttribute("title", "Student: "  + student.getFirst_name() + " " + student.getLast_name());
        Courses course = coursesRepository.findById(courseId).orElseThrow(null);
        student.addCourse(course);
        studentsRepository.save(student);
        Set<Courses> courses = student.getCourses();
        model.addAttribute("courses", courses);
        return "redirect:/students/" + studentId + "/courses";
    }
}
