package ua.pytaichuk.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.pytaichuk.models.Admin;
import ua.pytaichuk.services.AdminsService;

@Component
public class AdminRegValidator implements Validator {
    private final AdminsService adminsService;

    @Autowired
    public AdminRegValidator(AdminsService adminsService) {
        this.adminsService = adminsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Admin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Admin admin = (Admin) target;

        //Проверка на дубликат
        if (adminsService.findAdminByLogin(admin.getLogin()).isPresent()){
            errors.rejectValue("login", "", "Login already exists");
        }
    }
}
