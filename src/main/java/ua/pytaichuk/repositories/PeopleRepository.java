package ua.pytaichuk.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.pytaichuk.models.Person;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findAllByAdmin_IdAndSurnameStartingWith(int id, String surname, PageRequest of);
    List<Person> findAllByAdmin_Id(int id, PageRequest of);
    List<Person> findAllByGroup_IdAndAdmin_Id(int groupId, int adminId, PageRequest of);
    List<Person> findAllByGroup_IdAndAdmin_IdAndSurnameStartingWith(int groupId, int adminId, String surname, PageRequest of);
    int countAllByGroup_IdAndAdmin_Id(int groupId, int adminId);
    int countAllByGroup_IdAndAdmin_IdAndNameStartingWith(int groupId, int adminId, String surname);
}
