package ir.Hw13.service.exceptions;

public class AlreadyTaken extends RuntimeException{

    public AlreadyTaken(){
        System.out.println("You have already taken this test.");
    }
}
