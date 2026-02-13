package ir.Hw13.service;

import ir.Hw13.entity.TestQuestion;
import ir.Hw13.entity.Tests;
import jakarta.persistence.EntityManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExportService {
    private final EntityManager entityManager;
    private final TeacherServiceImpl teacherService;

    public ExportService(EntityManager entityManager, TeacherServiceImpl teacherService) {
        this.entityManager = entityManager;
        this.teacherService = teacherService;
    }


    public String exportTest(long teacherId, long testId) {
        int qNumber = 1;
        Tests test = teacherService.loadTeacherTest(teacherId, testId);

        String fileName = "test" + test.getId() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write(test.getTitle());
            writer.newLine();
            writer.write("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            writer.newLine();
            for (TestQuestion testQuestion : test.getTestQuestions()) {
                writer.write(qNumber + "- ");
                writer.write(testQuestion.getQuestions().buildQuestionText());
                writer.newLine();
                writer.write("-----------------------------------");
                writer.newLine();

                qNumber++;
            }


        } catch (IOException e) {
            throw new RuntimeException("Failed to export test file", e);
        }
        return fileName;
    }
}
