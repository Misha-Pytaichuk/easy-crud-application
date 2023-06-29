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
    private int globalSelectedId;
    private Admin admin;

    @Autowired
    public PeopleController(PeopleService peopleService, GroupsService groupsService) {
        this.peopleService = peopleService;
        this.groupsService = groupsService;
    }

    @GetMapping()
    public String index(Model model,
                        @ModelAttribute("group") Group group, HttpSession session,
                        @RequestParam(value = "search", required = false) String searchForSurname,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "count", required = false) Integer count,
                        @RequestParam(value = "totalCount", required = false) Integer totalCount,
                        @RequestParam(value = "selectedGroupId", required = false) Integer selectedGroupId
    ){
        admin = (Admin) session.getAttribute("admin");

        List<Person> people;
        List<Group> groups = groupsService.findAll();

            if(page == null || count == null){
                page = 0;
                count = 10;
            }
                if(group != null && group.getId() != 0) {
                    globalSelectedId = group.getId();
                    people = peopleService.search(globalSelectedId, admin.getId(), searchForSurname, page, count);
                    totalCount = peopleService.findTotalCountOfPeople(globalSelectedId, admin.getId(), searchForSurname);
                }
                else if(selectedGroupId != null && selectedGroupId != 0){
                    globalSelectedId = selectedGroupId;
                    people = peopleService.search(globalSelectedId, admin.getId(), searchForSurname, page, count);
                    totalCount = peopleService.findTotalCountOfPeople(globalSelectedId, admin.getId(), searchForSurname);
                } else{
                    globalSelectedId = 0;
                    people = peopleService.findAllByAdminIdAndSurname(admin.getId(), searchForSurname, page, count);
                    if(searchForSurname == null || searchForSurname.isEmpty()){
                        totalCount = peopleService.findTotalCountOfPeople(admin.getId());
                    } else totalCount = people.size();
                }

        session.setAttribute("search", searchForSurname);
        session.setAttribute("globalSelectedGroup", globalSelectedId);
        session.setAttribute("page", page);
        session.setAttribute("count", count);
        session.setAttribute("totalCount", totalCount);

        model.addAttribute("groups", groups);
        model.addAttribute("people", people);
        model.addAttribute("globalSelectedGroup", globalSelectedId);
        model.addAttribute("search", searchForSurname);
        model.addAttribute("page", page);
        model.addAttribute("count", count);
        model.addAttribute("totalCount", totalCount);

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, HttpSession session){
        Person person =  peopleService.findById(id);

        model.addAttribute("person", person);
        model.addAttribute("group", person.getGroup());
        model.addAttribute("url", sessionData(session));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model, @ModelAttribute("group") Group group, HttpSession session){
        model.addAttribute("person", new Person());
        model.addAttribute("groups", groupsService.findAll());
        model.addAttribute("url", sessionData(session));

        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, HttpSession session,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "people/new";
        peopleService.save(person, admin);
        return "redirect:" + sessionData(session);
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model, @ModelAttribute("group") Group group, HttpSession session){
        Person person =  peopleService.findById(id);

        model.addAttribute("person", person);
        model.addAttribute("groups", groupsService.findAll());
        model.addAttribute("url", sessionData(session));

        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()) return "people/edit";

        peopleService.update(person);
        return "redirect:" + sessionData(session);
    }

    @GetMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id, Model model){
        Person person = peopleService.findById(id);
        System.out.println(person.getId());
        model.addAttribute("person", person);
        return "people/delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session){
        System.out.println(id);
        peopleService.delete(id);
        return "redirect:" + sessionData(session);
    }

    private String sessionData(HttpSession session){
        String search = (String) session.getAttribute("search");
        if(search == null) search = "";
        int selected = (int) session.getAttribute("globalSelectedGroup");
        int page = (int) session.getAttribute("page");
        int count = (int) session.getAttribute("count");
        int totalCount = (int) session.getAttribute("totalCount");

        return "/people?search=" + search + "&page=" + page + "&count=" + count + "&totalCount=" + totalCount + "&selectedGroupId=" + selected;
    }
}
