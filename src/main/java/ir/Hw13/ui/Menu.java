package ir.Hw13.ui;

import ir.Hw13.dto.CourseDto;
import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.dto.PersonUpdateDto;
import ir.Hw13.dto.TestDto;
import ir.Hw13.entity.*;
import ir.Hw13.service.*;
import ir.Hw13.service.exceptions.TimeIsUp;
import ir.Hw13.util.ApplicationContext;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    Scanner inI = new Scanner(System.in);
    Scanner inS = new Scanner(System.in);
    private final StudentServiceImpl studentService;
    private final TeacherServiceImpl teacherService;
    private final ManagerService managerService;
    private final CourseService courseService;
    private final TestService testService;
    private final ExportService exportService;

    public Menu() {
        this.studentService = ApplicationContext.getInstance().getStudentService();
        this.teacherService = ApplicationContext.getInstance().getTeacherService();
        this.managerService = ApplicationContext.getInstance().getManagerService();
        this.courseService = ApplicationContext.getInstance().getCourseService();
        this.testService = ApplicationContext.getInstance().getTestService();
        this.exportService = ApplicationContext.getInstance().getExportService();
    }

    public void start() {
        while (true) {
            System.out.println("""
                    1. Sign up(student/teacher)
                    2. Log in
                    3. Exit
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> handleSignUp();
                case 2 -> handleLogIn();
                case 3 -> {
                    return;
                }
            }

        }
    }

    private void handleLogIn() {
        System.out.println("""
                Choose your roll:
                1. Manager
                2. Teacher
                3. Student
                4. Back
                """);

        int choice = inI.nextInt();
        Roll roll = null;
        switch (choice) {
            case 1 -> roll = Roll.MANAGER;
            case 2 -> roll = Roll.TEACHER;
            case 3 -> roll = Roll.STUDENT;
            case 4 -> {
                return;
            }
        }
        System.out.println("Enter your id: ");
        long id = inI.nextLong();
        System.out.println("Enter your password: ");
        String password = inS.nextLine();

        switch (roll) {
            case MANAGER -> {
                if (managerService.logIn(id, password)) {
                    showManagerMenu();
                } else {
                    System.out.println("Wrong input!");
                }
            }
            case TEACHER -> {
                if (teacherService.logIn(id, password)) {
                    showTeacherMenu(id);
                } else {
                    System.out.println("Wrong input!");
                }
            }
            case STUDENT -> {
                if (studentService.logIn(id, password)) {
                    showStudentMenu(id);
                } else {
                    System.out.println("Wrong input!");
                }
            }
            case null -> System.out.println("wrong input");
        }
    }

    private void showStudentMenu(long studentId) {
        while (true) {
            System.out.println("""
                    1. Show all of my course
                    2. Show the tests of a course
                    3. Take a test
                    """);

            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> handleShowStudentCourses(studentId);
                case 2 -> handleShowTestsOfCourse(studentId);
                case 3 -> handleTakeTest(studentId);
            }

        }
    }

    private void handleTakeTest(long studentId) {
        StudentTakeTestAttempt attempt = null;
        Tests test = null;
        System.out.println("Enter the test id: ");
        long testId = inI.nextLong();
        test = testService.loadTestById(testId);
        int numberOfQs = test.getTestQuestions().size();
        int count = 0;
        try {
            attempt = studentService.takeTest(studentId, test);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        assert attempt != null;
        while (true) {
            Duration remainingTime = testService.getRemainingTime(attempt);
            if (remainingTime.isZero()) {
                System.out.println("Time is up!!");
                testService.finishTest(attempt);
                return;
            }
            count++;
            System.out.println("Time remaining: " + remainingTime.getSeconds() / 60 + ":" + (remainingTime.getSeconds()) % 60);

            System.out.println(testService.showTestQuestions(attempt, test));

            System.out.println("Enter the id of the question you wanna answer: ");
            long questionId = inI.nextLong();
            Questions question = testService.loadQuestionById(questionId);
            int correctChoice = -1;
            String answerText = "";
            if (question instanceof MultipleChoiceQuestion) {
                System.out.println("Enter the correct choice: ");
                correctChoice = inI.nextInt();
            } else if (question instanceof DescriptiveQuestion) {
                System.out.println("Enter your answer: ");
                answerText = inS.nextLine();
            }
            try {
                testService.insertAnswerToTest(attempt, questionId, correctChoice, answerText);
            } catch (TimeIsUp timeIsUp) {
                System.out.println(timeIsUp.getMessage());
                testService.finishTest(attempt);
            }
            if (count >= numberOfQs) {
                System.out.println("Enter 0 to finish and 1 to continue: ");
                if (inI.nextInt() == 0) {
                    testService.finishTest(attempt);
                    return;
                }
            }
        }
    }

    private void handleShowTestsOfCourse(long studentId) {
        System.out.println("Enter the course id: ");
        long courseId = inI.nextLong();
        courseService.loadCourseTestsForStudent(studentId, courseId);
    }

    private void handleShowStudentCourses(long studentId) {
        System.out.println("Your courses: ");
        studentService.showStudentCourses(studentId);
    }


    private void showTeacherMenu(long teacherId) {
        System.out.println("hello teacher...");
        teacherService.showTeacherCourses(teacherId);
        while (true) {
            System.out.println("""
                    1. Show all the tests of the course
                    2. Show the tests you created for a course
                    3. Show the participants of a test
                    4. Add test to course
                    5. Edit test
                    6. Download test
                    7. Back
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> handleShowCourseTests();
                case 2 -> handleTeacherTests();
                case 3 -> handleShowParticipantsOfTest(teacherId);
                case 4 -> handleAddTest();
                case 5 -> handleEditTest();
                case 6 -> handleDownloadTest();
                case 7 -> {
                    return;
                }
            }
        }
    }

    private void handleShowParticipantsOfTest(long teacherId) {
        System.out.println("Enter the test id: ");
        long testId = inI.nextLong();
        List<StudentTakeTestAttempt> attempts = teacherService.loadFinishedTestCreatedByTeacherAttempts(testId, teacherId);


    }

    private void handleDownloadTest() {
        System.out.println("Enter your id: ");
        long teacherId = inI.nextLong();
        System.out.println("Enter the test id: ");
        long testId = inI.nextLong();
        String fileName = exportService.exportTest(teacherId, testId);
        System.out.println(fileName);

    }

    private void handleEditTest() {
        while (true) {
            System.out.println("""
                    1. Add questions to test
                    2. Back
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> handleAddQuestionToTest();
                case 2 -> {
                    return;
                }
            }
        }
    }

    private void handleAddQuestionToTest() {
        while (true) {
            System.out.println("""
                    1. Choose from question bank
                    2. Add a new question
                    3. Back
                    """);
            int choice = inI.nextInt();
            switch (choice) {
                case 1 -> handleAddFromQB();
                case 2 -> {
                    System.out.println("""
                            1. Multiple Choice Question
                            2. Descriptive Question
                            """);
                    int qType = inI.nextInt();
                    if (qType == 1) {
                        handleAddMCQs();
                    } else if (qType == 2) {
                        handleAddDQ();
                    } else {
                        System.out.println("Invalid input.");
                    }
                }
                case 3 -> {
                    return;
                }

            }

        }
    }

    private void handleAddFromQB() {
        System.out.println("Enter your id: ");
        long teacherId = inI.nextLong();
        System.out.println("Enter the course id: ");
        long courseId = inI.nextLong();
        System.out.println("Enter the test id: ");
        long testId = inI.nextLong();
        Tests test = testService.loadTestById(testId);

        List<Questions> qbForTeacher = teacherService.loadCourseQBForTeacher(teacherId, courseId);
        Map<Long, Questions> qMap = qbForTeacher.stream().collect(Collectors.toMap(BaseEntity::getId, questions -> questions));

        for (Questions question : qbForTeacher) {
            System.out.println("question id : " + question.getId());
            System.out.println("question Text : " + question.getText());
            System.out.println("---------------------------------------");
        }
        while (true) {
            System.out.println("Choose the question ids: ");
            long questionId = inI.nextLong();
            System.out.println("Determine the score: ");
            long score = inI.nextLong();
            teacherService.addQToTest(qMap.get(questionId), test, score);
            System.out.println("Continue? press 1 and enter -1 to end: ");
            if (inI.nextInt() == -1) {
                return;
            }
        }
    }

    private void handleAddDQ() {
        System.out.println("Enter your id: ");
        long teacherId = inI.nextLong();
        System.out.println("Enter the course id: ");
        long courseId = inI.nextLong();
        System.out.println("Enter the title of the question: ");
        String title = inS.nextLine();
        System.out.println("Enter the question text: ");
        String text = inS.nextLine();
        DescriptiveQuestion dQ = teacherService.makeDQs(teacherId, courseId, title, text);
        System.out.println("Enter the test id: ");
        long testId = inI.nextLong();
        Tests test = testService.loadTestById(testId);
        System.out.println("Determine the score: ");
        long score = inI.nextLong();
        teacherService.addQToTest(dQ, test, score);
    }


    private void handleAddMCQs() {
        System.out.println("Enter your id: ");
        long teacherId = inI.nextLong();
        System.out.println("Enter the course id: ");
        long courseId = inI.nextLong();
        System.out.println("Enter the title of the question: ");
        String title = inS.nextLine();
        System.out.println("Enter the question text: ");
        String text = inS.nextLine();
        Map<Choice, Boolean> choiceList = handleMakeChoices();
        MultipleChoiceQuestion mCQ = teacherService.makeMCQs(teacherId, courseId, title, text, choiceList);
        System.out.println("Enter the test id: ");
        long testId = inI.nextLong();
        Tests test = testService.loadTestById(testId);
        System.out.println("Determine the score: ");
        long score = inI.nextLong();
        teacherService.addQToTest(mCQ, test, score);
    }

    private Map<Choice, Boolean> handleMakeChoices() {
        Map<Choice, Boolean> choiceList = new HashMap<>();
        int counter = 0;
        while (true) {
            if (counter >= 2) {
                System.out.println("Enter -1 to exit or 1 to continue: ");
                int exit = inI.nextInt();
                if (exit == -1) {
                    return choiceList;
                }
            }
            System.out.println("Enter the choice: ");
            String text = inS.nextLine();

            System.out.println("""
                    Is it the answer?
                    1. yes
                    2. no
                    """);
            int isAnswerI = Integer.parseInt(inS.nextLine());
            boolean isAnswerB = isAnswerI == 1;
            choiceList.put(teacherService.makeChoice(text), isAnswerB);
            counter++;
        }
    }


    private void handleAddTest() {
        System.out.println("Enter your id: ");
        long teacherId = inI.nextLong();
        System.out.println("Enter the course id: ");
        long courseId = inI.nextLong();
        System.out.println("Enter the title: ");
        String title = inS.nextLine();
        System.out.println("Enter the description: ");
        String description = inS.nextLine();
        System.out.println("Enter the date(yyyy-mm-dd): ");
        String inputDate = inS.nextLine();
        LocalDate date = LocalDate.parse(inputDate);
        System.out.println("Enter the duration: ");
        Integer duration = inI.nextInt();
        Person person = managerService.loadPersonById(teacherId);
        Course course = managerService.loadCourseById(courseId);
        TestDto dto = new TestDto(title, description, date, (Teacher) person, course, duration);
        testService.AddTest(dto);
    }

    private void handleTeacherTests() {
        System.out.println("Enter the course id: ");
        long courseId = inI.nextLong();
        System.out.println("Enter your id: ");
        long teacherId = inI.nextLong();
        teacherService.showTeacherCourseTests(teacherId, courseId);

    }

    private void handleShowCourseTests() {
        System.out.println("Enter the course id: ");
        long courseId = inI.nextLong();
        courseService.loadCourseTests(courseId);
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
                    9. Show course
                    10. Remove teacher from course
                    11. Remove student from course
                    12. Back
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
                case 9 -> handleShowCourse();
                case 10 -> handleRemoveTeacherFromCourse();
                case 11 -> handleRemoveStudentFromCourse();
                case 12 -> {
                    return;
                }
            }

        }

    }

    private void handleRemoveStudentFromCourse() {
        System.out.println("Enter the student id: ");
        int studentId = inI.nextInt();
        System.out.println("Enter the course name: ");
        String courseTitle = inS.nextLine();
        managerService.removeStudentFromCourse(studentId, courseTitle);
    }

    private void handleRemoveTeacherFromCourse() {
        System.out.println("Enter the course's title: ");
        String title = inS.nextLine();
        managerService.removeTeacherFromCourse(title);

    }

    private void handleShowCourse() {
        System.out.println("Enter the title of the course: ");
        String courseTitle = inS.nextLine();
        managerService.loadCourseByTitle(courseTitle);
    }

    private void handleAddStudentToCourse() {
        //managerService.show();
        System.out.println("Enter the title of the course: ");
        String title = inS.nextLine();
        System.out.println("Enter the id of the student: ");
        long id = inI.nextLong();
        try {
            int isAdded = managerService.addStudentToCourse(title, id);
            if (isAdded == 1) System.out.println("Added successfully.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleAddTeacherToCourse() {
        System.out.println("Enter the title of the course: ");
        String title = inS.nextLine();
        System.out.println("Enter the id of the teacher: ");
        long id = inI.nextLong();
        try {
            int isAdded = managerService.addTeacherToCourse(title, id);
            if (isAdded == 1) System.out.println("Added successfully.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
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
        Person fetchedPerson = managerService.loadPersonById(id);
        System.out.println(fetchedPerson + "Roll: " + fetchedPerson.getClass().getSimpleName());
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
                    if (fetchedPerson.getClass().getSimpleName().equals("Student")) {
                        if (managerService.isStudentInvolvedInCourses(id)) {
                            System.out.println("Can't change the roll. Student is involved in a course. ");
                        } else {
                            newRoll = "Teacher";
                            System.out.println("You changed the roll.");
                        }
                    } else {
                        if (managerService.isTeacherInvolvedInCourses(id)) {
                            System.out.println("Can't change the roll. Teacher is involved in a course. ");
                        } else {
                            newRoll = "Student";
                            System.out.println("You changed the roll.");
                        }
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
        boolean isDone;
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

