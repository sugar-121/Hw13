package ir.Hw13.service.exceptions;

public class NotAStudentException extends RuntimeException{

    public NotAStudentException(long id) {
        System.out.println("Person with the id of " + id + " is not a student.");
    }
}
