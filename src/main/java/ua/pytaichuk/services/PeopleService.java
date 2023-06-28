package ua.pytaichuk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public List<Person> findAllByAdminIdAndSurname(int id, String surname, int page, int count){
        return surname != null ? peopleRepository.findAllByAdmin_IdAndSurnameStartingWith(id, surname, PageRequest.of(page, count)) : peopleRepository.findAllByAdmin_Id(id, PageRequest.of(page, count));
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public List<Person> search(int groupId, int adminId, String surname, int page, int count)
    {
       return surname != null ? peopleRepository.findAllByGroup_IdAndAdmin_IdAndSurnameStartingWith(groupId, adminId, surname, PageRequest.of(page, count)) : peopleRepository.findAllByGroup_IdAndAdmin_Id(groupId, adminId, PageRequest.of(page, count));
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

    public int findTotalCountOfPeople(){
        return peopleRepository.findAll().size();
    }

    public int findTotalCountOfPeople(int groupId, int adminId, String surname){
        return surname != null ? peopleRepository.countAllByGroup_IdAndAdmin_IdAndNameStartingWith(groupId, adminId, surname) : peopleRepository.countAllByGroup_IdAndAdmin_Id(groupId, adminId);
    }
}
