package ir.Hw13.service.exceptions;

public class NotATeacherException extends RuntimeException{

    public NotATeacherException(long id) {
        System.out.println("Person with the id of " + id + " is not a teacher.");
    }
}
