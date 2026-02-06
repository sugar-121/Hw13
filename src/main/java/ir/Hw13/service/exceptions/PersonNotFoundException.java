package ir.Hw13.service.exceptions;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(long id) {
        System.out.println("Person with the id of " + id + " not exists.");
    }
}
