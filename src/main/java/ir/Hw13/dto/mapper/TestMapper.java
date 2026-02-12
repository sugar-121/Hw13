package ir.Hw13.dto.mapper;

import ir.Hw13.dto.TestDto;
import ir.Hw13.entity.Tests;


public class TestMapper {

    public void loadTest(Tests test){
        System.out.println("Id: " + test.getId());
        System.out.println("Title: " + test.getTitle());
        System.out.println("Description: " + test.getDescription());
        System.out.println("Teacher: " + test.getTeacher().getFirstName()+ " " + test.getTeacher().getLastName());
    }

    public Tests toEntity(TestDto dto){
        Tests test = new Tests();
        test.setTitle(dto.getTitle());
        test.setDescription(dto.getDescription());
        test.setDateTime(dto.getDate());
        test.setTeacher(dto.getTeacher());
        test.setCourse(dto.getCourse());
        return test;
    }
}
