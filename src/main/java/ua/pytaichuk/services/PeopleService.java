package ua.pytaichuk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.pytaichuk.models.Admin;
import ua.pytaichuk.models.Person;
import ua.pytaichuk.repositories.PeopleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAllByAdminId(int id){
        return peopleRepository.findAllByAdmin_Id(id);
    }

    public List<Person> findAllByGroupIdAndAdminId(int groupId, int adminId){
        return peopleRepository.findAllByGroup_IdAndAdmin_Id(groupId, adminId);
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person, Admin admin){
        person.setAdmin(admin);
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Person updatedPerson){
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        System.out.println("delete "+id);
        peopleRepository.deleteById(id);

    }
}
