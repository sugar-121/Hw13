package ir.Hw13.service.exceptions;

public class IllegalTestAccess extends RuntimeException {
    public IllegalTestAccess() {
        System.out.println("You don't have access to this test!!");
    }

}
