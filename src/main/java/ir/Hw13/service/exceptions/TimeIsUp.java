package ir.Hw13.service.exceptions;

public class TimeIsUp extends RuntimeException{
    public TimeIsUp() {
        System.out.println("time is finished.");
    }
}
