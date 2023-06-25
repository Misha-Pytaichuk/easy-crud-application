package ua.pytaichuk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.pytaichuk.models.Person;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findAllByAdmin_Id(int id);
    List<Person> findAllByGroup_IdAndAdmin_Id(int groupId, int adminId);
}
