package ir.Hw13.dto.mapper;

import ir.Hw13.dto.TestDto;
import ir.Hw13.entity.TestQuestion;
import ir.Hw13.entity.Tests;

import java.util.Set;


public class TestMapper {

    public void loadTest(Tests test) {
        long totalScore = 0;
        System.out.println("Id: " + test.getId());
        System.out.println("Title: " + test.getTitle());
        System.out.println("Description: " + test.getDescription());
        System.out.println("Teacher: " + test.getTeacher().getFirstName() + " " + test.getTeacher().getLastName());
        for (TestQuestion question : test.getTestQuestions()) {
            totalScore += question.getScore();
        }
        System.out.println("Score: " + totalScore);
    }

    public Tests toEntity(TestDto dto) {
        Tests test = new Tests();
        test.setTitle(dto.getTitle());
        test.setDescription(dto.getDescription());
        test.setDateTime(dto.getDate());
        test.setTeacher(dto.getTeacher());
        test.setCourse(dto.getCourse());
        return test;
    }
}
