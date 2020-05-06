package ua.com.foxminded.sqljdbcschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.com.foxminded.sqljdbcschool.models.Groups;
import ua.com.foxminded.sqljdbcschool.repo.GroupsRepository;

@Controller
public class GroupsController {

    @Autowired
    private GroupsRepository groupsRepository;

    @GetMapping("/groups")
    public String groupsMainPage(Model model) {
        model.addAttribute("title", "Groups");
        Iterable<Groups> groups = groupsRepository.findAll();
        model.addAttribute("groups", groups);
        return "groups-main-page";
    }
}
