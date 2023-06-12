package ua.pytaichuk.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.pytaichuk.dao.AdminDAO;
import ua.pytaichuk.models.Admin;

@Component
public class AdminRegValidator implements Validator {
    private final AdminDAO adminDAO;

    @Autowired
    public AdminRegValidator(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Admin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Admin admin = (Admin) target;

        //Проверка на дубликат
        if (adminDAO.show(admin.getLogin()).isPresent()){
            errors.rejectValue("login", "", "Login already exists");
        }
    }
}
