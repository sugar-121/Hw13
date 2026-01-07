package ir.Hw13.ui;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.entity.Manager;
import ir.Hw13.service.ManagerService;
import ir.Hw13.service.StudentServiceImpl;
import ir.Hw13.service.TeacherServiceImpl;
import ir.Hw13.util.ApplicationContext;

import java.util.Scanner;

public class Menu {
    Scanner inI = new Scanner(System.in);
    Scanner inS = new Scanner(System.in);
    private StudentServiceImpl studentService;
    private TeacherServiceImpl teacherService;
    private ManagerService managerService;

    public Menu() {
        this.studentService = ApplicationContext.getInstance().getStudentService();
        this.teacherService = ApplicationContext.getInstance().getTeacherService();
        this.managerService = ApplicationContext.getInstance().getManagerService();
    }

    public void start() {
        while (true) {
            System.out.println("""
                    1. Sign up(student/teacher)
                    2. Log in (only for manager)
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> handleSignUp();
                case 2 -> handleLogIn();
            }

        }
    }

    private void handleLogIn() {
        System.out.println("Enter your id: ");
        long id = inI.nextLong();
        System.out.println("Enter your password: ");
        String password = inS.nextLine();

        if (managerService.logIn(id, password)) {
            showManagerMenu();
        } else {
            System.out.println("Wrong input!");
        }

    }

    private void showManagerMenu() {
        System.out.println("Welcome manager. Choose your service: ");
        while (true) {
            System.out.println("""
                    1. Show sign up requests
                    2. Submit sign up requests
                    3. Edit user
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> System.out.println(managerService.loadSignUpRequests());
                case 2 -> handleSubmit();
            }


        }

    }

    private void handleSubmit() {
        while (true) {
            System.out.println("""
                    1. Submit all
                    2. Submit one
                    3. Back
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> managerService.submitAll();
                case 2 -> submitOne();
                case 3 -> {
                    return;
                }
            }
        }
    }

    private void submitOne() {
        System.out.println("Enter the id to submit: ");
        long id = inI.nextLong();
        managerService.submitOne(id);
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
                roll = inI.nextInt();
            }
        } while (!isValid);
        boolean isDone = false;
        PersonSignUpDto signUpDto = new PersonSignUpDto();
        signUpDto.setFirstName(firstName);
        signUpDto.setLastName(lastName);
        signUpDto.setPassword(password);
        if (roll == 1) {
            isDone = studentService.signUp(signUpDto);
        } else {
            isDone = teacherService.signUp(signUpDto);
        }
        if (isDone) {
            System.out.println("done");
        }
    }
}
