package ir.Hw13.ui;

import ir.Hw13.dto.CourseDto;
import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.PersonUpdateDto;
import ir.Hw13.entity.Person;
import ir.Hw13.entity.Status;
import ir.Hw13.service.ManagerService;
import ir.Hw13.service.StudentServiceImpl;
import ir.Hw13.service.TeacherServiceImpl;
import ir.Hw13.util.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
                    4. Search user
                    5. Add course
                    6. Drop course
                    7. Set teacher for a course
                    8. Add student to a course
                    9. Back
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> System.out.println(managerService.loadSignUpRequests());
                case 2 -> handleSubmit();
                case 3 -> handleEditUser();
                case 4 -> handleSearchUser();
                case 5 -> handleAddCourse();
                case 6 -> handleDropCourse();
                case 7 -> handleAddTeacherToCourse();
                case 8 -> handleAddStudentToCourse();
                case 9 -> {
                    return;
                }
            }

        }

    }

    private void handleAddStudentToCourse() {
        System.out.println("Enter the title of the course: ");
        String title = inS.nextLine();
        System.out.println("Enter the id of the student: ");
        long id = inI.nextLong();
        managerService.addStudentToCourse(title, id);

    }

    private void handleAddTeacherToCourse() {
        System.out.println("Enter the title of the course: ");
        String title = inS.nextLine();
        System.out.println("Enter the id of the teacher: ");
        long id = inI.nextLong();
        managerService.addTeacherToCourse(title, id);
    }

    private void handleDropCourse() {
        System.out.println("Enter the title: ");
        String title = inS.nextLine();
        if (managerService.dropCourse(title)) {
            System.out.println("Dropped successfully.");
        } else {
            System.out.println("Course doesn't exist.");
        }
    }

    private void handleAddCourse() {
        System.out.println("Enter the title of the course: ");
        String title = inS.nextLine();
        System.out.println("Enter the beginning date (yyyy-mm-dd): ");
        String beginningInput = inS.nextLine();
        LocalDate beginning = LocalDate.parse(beginningInput);
        System.out.println("Enter the ending date (yyyy-mm-dd): ");
        String endingInput = inS.nextLine();
        LocalDate ending = LocalDate.parse(endingInput);
        CourseDto dto = new CourseDto(title, beginning, ending);
        if (managerService.addCourse(dto)) {
            System.out.println("Added successfully");
        } else {
            System.out.println("Some thing went wrong");
        }
    }

    private void handleSearchUser() {
        System.out.println("Choose filters: ");
        String filteredType = null;
        String filteredFirstName = null;
        String filteredLastName = null;
        while (true) {
            System.out.println("""
                    1. Roll
                    2. First name
                    3. Last name
                    4. Apply filters
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter (1) for student and (2) for teacher: ");
                    int type = inI.nextInt();
                    if (type == 1) {
                        filteredType = "Student";
                    } else if (type == 2) {
                        filteredType = "Teacher";
                    } else {
                        System.out.println("Invalid");
                    }
                }
                case 2 -> {
                    System.out.println("Enter the pattern of the first name: ");
                    filteredFirstName = inS.nextLine();
                }
                case 3 -> {
                    System.out.println("Enter the pattern of the last name: ");
                    filteredLastName = inS.nextLine();
                }
                case 4 -> {
                    List<Person> filteredList = managerService.applyFilter(filteredType, filteredFirstName, filteredLastName);
                    if (!filteredList.isEmpty()) {
                        filteredList.forEach(System.out::println);
                    } else {
                        System.out.println("Such person doesn't exist.");
                    }
                }
            }
        }
    }

    private void handleEditUser() {
        System.out.println("Enter the id of the user: ");
        long id = inI.nextLong();
        Person fetchedPerson = managerService.loadById(id);
        System.out.println(fetchedPerson);
        String newFirstName = null;
        String newLastName = null;
        String newRoll = null;
        while (true) {
            System.out.println("""
                    What do you want to edit?
                    1.First name
                    2.Last name
                    3.Change roll
                    4.Commit changes
                    5.Back
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the new first name:");
                    newFirstName = inS.nextLine();
                }
                case 2 -> {
                    System.out.println("Enter the new Last name:");
                    newLastName = inS.nextLine();
                }
                case 3 -> {
                    System.out.println("You changed the roll.");
                    if (fetchedPerson.getClass().getSimpleName().equals("Student")) {
                        newRoll = "Teacher";
                    } else {
                        newRoll = "Student";
                    }
                }
                case 4 -> {
                    PersonUpdateDto personUpdateDto = new PersonUpdateDto(newFirstName, newLastName, newRoll);
                    managerService.updateUser(personUpdateDto, fetchedPerson);
                }
                case 5 -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid input!");
                    return;
                }
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
