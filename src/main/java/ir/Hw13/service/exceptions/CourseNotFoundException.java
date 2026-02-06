package ir.Hw13.service.exceptions;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String title) {
        System.out.println("Course with the title of " + title + " not exists.");
    }
}
