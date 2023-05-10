package ua.pytaichuk.dao;

import org.springframework.stereotype.Component;
import ua.pytaichuk.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private List<Person> people;
    private static int AUTO_INCREMENT_ID = 0;

    {
        people = new ArrayList<>();
        people.add(new Person(AUTO_INCREMENT_ID++, "Misha"));
        people.add(new Person(AUTO_INCREMENT_ID++, "Nikita"));
        people.add(new Person(AUTO_INCREMENT_ID++, "Danil"));
        people.add(new Person(AUTO_INCREMENT_ID++, "Slava"));
        people.add(new Person(AUTO_INCREMENT_ID++, "Zhenya"));
        people.add(new Person(AUTO_INCREMENT_ID++, "Andrei"));
        people.add(new Person(AUTO_INCREMENT_ID++, "Artem"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        for (Person person: people) {
            if(person.getId() == id) return person;
        }
        return null;
    }

    public void save(Person person) {
        person.setId(AUTO_INCREMENT_ID++);
        people.add(person);
    }

    public void delete(int id) {
        people.removeIf(p->p.getId() == id);
    }

    public void edit(int id, Person person) {
        Person toBeUpdatePerson = show(id);

        toBeUpdatePerson.setName(person.getName());
    }
}
