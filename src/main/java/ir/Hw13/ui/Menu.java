package ir.Hw13.ui;

import java.util.Scanner;

public class Menu {
    Scanner inI = new Scanner(System.in);
    Scanner inS = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("""
                    1. Sign up(student/teacher)
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> handleSignUp();
            }

        }
    }

    private void handleSignUp() {
        System.out.println("Enter your first name: ");
        String firstName = inS.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = inS.nextLine();
        System.out.println("Enter your password(more than 8 characters): ");
        String password = inS.nextLine();
        System.out.println("Signing up as student(1) or teacher(2)? ");
        boolean isValid = true;
        int roll = inI.nextInt();
        do {
            if (roll != 1 && roll != 2) {
                System.out.println("student(1)   teacher(2)");
                isValid = false;
            }
            roll = inI.nextInt();
        } while (!isValid);

        if (roll == 1) {
            //
        } else {
            //
        }


    }
}
