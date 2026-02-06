package ir.Hw13.dto.mapper;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.PersonUpdateDto;
import ir.Hw13.entity.Person;
import ir.Hw13.entity.Status;
import ir.Hw13.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PersonMapper{

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

    public List<Person> submitAll(List<Person> people) {
        people.forEach(person -> person.setStatus(Status.SUBMITTED));
        return people;
    }

    public Person submitOne(Person person) {
        person.setStatus(Status.SUBMITTED);
        return person;
    }

    public Person mapToEntityForUpdate(PersonUpdateDto dto, Person fetchedPerson) {
        if (dto.getFirstName() != null) {
            fetchedPerson.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            fetchedPerson.setLastName(dto.getLastName());
        }
        return fetchedPerson;
    }

}
