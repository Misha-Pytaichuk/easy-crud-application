package ua.pytaichuk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.pytaichuk.models.Admin;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer > {
     Admin findAdminByLoginAndPassword(String login, String password);
     Optional<Admin> findAdminByLogin(String login);
}
