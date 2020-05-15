package ua.com.foxminded.sqljdbcschool.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.com.foxminded.sqljdbcschool.repo.GroupsRepository;
import ua.com.foxminded.sqljdbcschool.repo.StudentsRepository;

@Controller
public class GroupsController {

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @GetMapping("/groups")
    public String groupsMainPage(Model model) {
        model.addAttribute("title", "Groups");

        Map<Integer, String> groups = new HashMap<>();
        groupsRepository.findAll().forEach(group -> groups.put(group.getGroup_id(), group.getGroup_name()));

        List<String[]> groupsWithNumberOfStudents = new ArrayList<>();
        for (int i = 1; i <= groups.size(); i++) {
            String[] info = new String[3];
            info[0] = Integer.toString(i);
            info[1] = groups.get(i);
            info[2] = Integer.toString(studentsRepository.countAllByGroup(i));
            groupsWithNumberOfStudents.add(info);
        }
        
        model.addAttribute("groups", groupsWithNumberOfStudents);
        return "groups-main-page";
    }
    
    //@PostMapping("/groups")
}
