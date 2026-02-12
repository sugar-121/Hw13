package ir.Hw13.service;

import ir.Hw13.dto.mapper.CourseMapper;
import ir.Hw13.dto.mapper.TestMapper;
import ir.Hw13.entity.Course;
import ir.Hw13.entity.Tests;
import ir.Hw13.repository.CourseRepository;
import ir.Hw13.util.ApplicationContext;

import java.util.List;

public class CourseService {
    private final ApplicationContext context;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final TestMapper testMapper;

    public CourseService() {
        this.context = ApplicationContext.getInstance();
        this.courseRepository = context.getCourseRepository();
        this.courseMapper = context.getCourseMapper();
        this.testMapper = context.getTestMapper();
    }


    public void showCourseTests(long courseId) {
        Course course = courseRepository.fetchCourseById(courseId);
        for (Tests test : course.getTests()) {
            testMapper.loadTest(test);
        }
    }
}

