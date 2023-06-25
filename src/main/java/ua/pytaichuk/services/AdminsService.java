package ua.pytaichuk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.pytaichuk.models.Admin;
import ua.pytaichuk.repositories.AdminRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminsService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin findAdminByLoginAndPassword(Admin admin){
        return adminRepository.findAdminByLoginAndPassword(admin.getLogin().trim(),  admin.getPassword().trim());
    }
    public void save(Admin admin) {
        adminRepository.save(admin);
    }
    public Optional<Admin> findAdminByLogin(String login){
        return adminRepository.findAdminByLogin(login);
    }
}
