package ua.pytaichuk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.pytaichuk.models.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
