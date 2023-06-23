package ua.pytaichuk.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.pytaichuk.dao.AdminDAO;
import ua.pytaichuk.models.Admin;
import ua.pytaichuk.util.AdminRegValidator;

@Controller
@RequestMapping("/admins")
public class AdminsController{

    private final AdminDAO adminDAO;
    final private AdminRegValidator adminRegValidator;

    @Autowired
    public AdminsController(AdminDAO adminDAO, AdminRegValidator adminRegValidator) {
        this.adminDAO = adminDAO;
        this.adminRegValidator = adminRegValidator;
    }

    @GetMapping("/new")
    public String newAdmin(Model model){
        model.addAttribute("admin", new Admin());
        return "admins/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("admin") @Valid Admin admin,
                         BindingResult bindingResult, HttpSession session){
        adminRegValidator.validate(admin, bindingResult);
        if(bindingResult.hasErrors()) return "admins/new";

        adminDAO.save(admin);

        session.setAttribute("admin", admin);
        return "redirect:/people?indexGroupId=0";
    }

    @GetMapping("/login/get")
    public String loginAdmin(Model model){
        model.addAttribute("admin", new Admin());
        return "admins/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("admin") @Valid Admin admin,
                         BindingResult bindingResult, HttpSession session){
        if (bindingResult.hasErrors()) {
            return "admins/login";
        }

        admin = adminDAO.login(admin);
        admin.setPassword(null);
        session.setAttribute("admin", admin);
        return "redirect:/people?indexGroupId=0";
    }
}
