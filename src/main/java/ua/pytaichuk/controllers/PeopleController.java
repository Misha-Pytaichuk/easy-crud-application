package ua.pytaichuk.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.pytaichuk.models.Admin;
import ua.pytaichuk.models.Group;
import ua.pytaichuk.models.Person;
import ua.pytaichuk.services.GroupsService;
import ua.pytaichuk.services.PeopleService;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final GroupsService groupsService;
    private Group selectedGroup;
    private Admin admin;

    @Autowired
    public PeopleController(PeopleService peopleService, GroupsService groupsService) {
        this.peopleService = peopleService;
        this.groupsService = groupsService;
    }

    @GetMapping()
    public String index(Model model,
                        @ModelAttribute("group") Group group,
                        HttpSession session,
                        @RequestParam(value = "indexGroupId", required = false) Integer selectedGroupId){
        admin = (Admin) session.getAttribute("admin");
        List<Person> people;
        List<Group> groups = groupsService.findAll();

            if(selectedGroupId != null) {
                if(selectedGroupId == 0){
                    people = peopleService.findAllByAdminId(admin.getId());
                } else {
                    selectedGroup = groupsService.findById(selectedGroupId);
                    group = selectedGroup;
                    System.out.println("selectedGroupId " + selectedGroupId);
                    System.out.println("group " + group.getId());
                }
            }
            if(group != null){
                people = group.getId() == 0 ? peopleService.findAllByAdminId(admin.getId()) : peopleService.findAllByGroupIdAndAdminId(group.getId(), admin.getId());
                selectedGroup = group;
                System.out.println("selectedGroup.getId() " + selectedGroup.getId());
            }
            else
                people = peopleService.findAllByAdminId(admin.getId());

        model.addAttribute("groups", groups);
        model.addAttribute("people", people);
        model.addAttribute("globalSelectedGroup", selectedGroup);
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Person person =  peopleService.findById(id);
        model.addAttribute("person", person);
        model.addAttribute("group", person.getGroup());
        model.addAttribute("globalSelectedGroup", selectedGroup);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model, @ModelAttribute("group") Group group){
        model.addAttribute("person", new Person());
        model.addAttribute("globalSelectedGroup", selectedGroup);
        model.addAttribute("groups", groupsService.findAll());

        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "people/new";
        peopleService.save(person, admin);
        return "redirect:/people?indexGroupId=" + selectedGroup.getId();
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model, @ModelAttribute("group") Group group){
        Person person =  peopleService.findById(id);

        model.addAttribute("person", person);
        model.addAttribute("globalSelectedGroup", selectedGroup);
        model.addAttribute("groups", groupsService.findAll());
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "people/edit";

        peopleService.update(person);
        return "redirect:/people?indexGroupId=" + selectedGroup.getId();
    }

    @GetMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id, Model model){
        Person person = peopleService.findById(id);
        System.out.println(person.getId());
        model.addAttribute("person", person);
        return "people/delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        System.out.println(id);
        peopleService.delete(id);
        return "redirect:/people?indexGroupId=" + selectedGroup.getId();
    }
}
