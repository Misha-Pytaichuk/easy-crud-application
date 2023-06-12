package ua.pytaichuk.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.pytaichuk.dao.GroupDAO;
import ua.pytaichuk.dao.PersonDAO;
import ua.pytaichuk.models.Admin;
import ua.pytaichuk.models.Group;
import ua.pytaichuk.models.Person;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final GroupDAO groupDAO;
    private Group selectedGroup;
    @Autowired
    public PeopleController(PersonDAO personDAO, GroupDAO groupDAO) {
        this.personDAO = personDAO;
        this.groupDAO = groupDAO;
    }

    @GetMapping()
    public String index(Model model,
                        @ModelAttribute("group") Group group,
                        HttpSession session,
                        @RequestParam(value = "indexGroupId", required = false) Integer selectedGroupId){
        Admin admin = (Admin) session.getAttribute("admin");
        List<Person> people;
        List<Group> groups = groupDAO.show();

            if(selectedGroupId != null) {
                if(selectedGroupId == 0){
                    people = personDAO.index(admin);
                } else {
                    selectedGroup = groupDAO.show(selectedGroupId);
                    group = selectedGroup;
                    System.out.println("selectedGroupId " + selectedGroupId);
                    System.out.println("group " + group.getId());
                }
            }
            if(group != null){
                people = group.getId() == 0 ? personDAO.index(admin) : personDAO.indexByGroupId(admin, group.getId());
                selectedGroup = group;
                System.out.println("selectedGroup.getId() " + selectedGroup.getId());
            }
            else
                people = personDAO.index(admin);

        model.addAttribute("groups", groups);
        model.addAttribute("people", people);
        model.addAttribute("globalSelectedGroup", selectedGroup);
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("globalSelectedGroup", selectedGroup);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model, @ModelAttribute("group") Group group){
        model.addAttribute("person", new Person());
        model.addAttribute("globalSelectedGroup", selectedGroup);
        model.addAttribute("groups", groupDAO.show());

        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "people/new";
        personDAO.save(person);
        return "redirect:/people?indexGroupId=" + selectedGroup.getId();
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model, @ModelAttribute("group") Group group){
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("globalSelectedGroup", selectedGroup);
        model.addAttribute("groups", groupDAO.show());
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "people/edit";
        personDAO.edit(id ,person);
        return "redirect:/people?indexGroupId=" + selectedGroup.getId();
    }

    @GetMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people?indexGroupId=" + selectedGroup.getId();
    }
}
