package ir.Hw13.dto.mapper;

import ir.Hw13.entity.Person;
import ir.Hw13.entity.Status;

import java.util.ArrayList;
import java.util.List;

public class PersonMapper {

    public List<String> personLoader(List<Person> personList) {
        List<String> people = new ArrayList<>();
        for (Person p : personList) {
            people.add(
                    "Id: " + p.getId() + System.lineSeparator() +
                            "Roll: " + p.getClass().getSimpleName() + System.lineSeparator() +
                            "First name: " + p.getFirstName() + System.lineSeparator() +
                            "Last name: " + p.getLastName() + System.lineSeparator() +
                            "Status: " + p.getStatus() + System.lineSeparator() +
                            "---------------------------------------------" + System.lineSeparator()
            );
        }
        return people;
    }
    public List<Person> submitAll(List<Person> people){
        people.forEach(person -> person.setStatus(Status.SUBMITTED));
        return people;
    }

    public Person submitOne(Person person){
        person.setStatus(Status.SUBMITTED);
        return person;
    }


}
